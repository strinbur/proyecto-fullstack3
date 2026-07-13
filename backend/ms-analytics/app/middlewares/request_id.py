import uuid

from starlette.middleware.base import BaseHTTPMiddleware
from starlette.requests import Request

from app.core.request_context import set_request_id

HEADER_NAME = "X-Request-Id"


class RequestIdMiddleware(BaseHTTPMiddleware):
    async def dispatch(self, request: Request, call_next):
        request_id = request.headers.get(HEADER_NAME) or str(uuid.uuid4())
        set_request_id(request_id)

        response = await call_next(request)
        response.headers[HEADER_NAME] = request_id
        return response