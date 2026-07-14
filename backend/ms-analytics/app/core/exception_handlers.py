from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from starlette.exceptions import HTTPException as StarletteHTTPException

from app.core import glitchtip_reporter


def register_exception_handlers(app: FastAPI) -> None:

    @app.exception_handler(StarletteHTTPException)
    async def http_exception_handler(request: Request, exc: StarletteHTTPException) -> JSONResponse:
        status = exc.status_code

        if status >= 500:
            # Errores de infraestructura/downstream reales (502, 503, 504 hacia
            # ms-data-aggregation, por ejemplo) - si son importantes.
            glitchtip_reporter.capture_message(
                f"Error {status} en llamada downstream o infraestructura: {exc.detail}",
                level="error",
            )
        elif status in (401, 403):
            glitchtip_reporter.capture_message(
                f"Acceso denegado ({status}): {exc.detail}",
                level="warning",
            )
        elif status == 404:
            glitchtip_reporter.capture_message(
                f"Recurso no encontrado: {exc.detail}",
                level="info",
            )
        else:
            glitchtip_reporter.capture_message(
                f"Error de negocio ({status}): {exc.detail}",
                level="info",
            )

        return JSONResponse(
            status_code=status,
            content={"status": status, "errores": [str(exc.detail)]},
        )

    @app.exception_handler(Exception)
    async def global_exception_handler(request: Request, exc: Exception) -> JSONResponse:
        glitchtip_reporter.capture_exception(exc, "Excepcion no controlada en ms-analytics")

        return JSONResponse(
            status_code=500,
            content={
                "status": 500,
                "errores": ["Error interno del servidor"],
            },
        )