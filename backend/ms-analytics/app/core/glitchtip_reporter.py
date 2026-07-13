import logging

import sentry_sdk

from app.core.request_context import get_request_id

logger = logging.getLogger("glitchtip")


def capture_exception(exc: Exception, message: str | None = None) -> None:
    request_id = get_request_id()

    if message:
        logger.error("%s [requestId=%s]: %s", message, request_id, exc, exc_info=exc)
    else:
        logger.error("Error capturado para envio a GlitchTip [requestId=%s]", request_id, exc_info=exc)

    with sentry_sdk.push_scope() as scope:
        scope.set_tag("request_id", request_id)
        scope.set_extra("request_id", request_id)
        if message:
            scope.set_extra("context", message)
        sentry_sdk.capture_exception(exc)


def capture_message(message: str, level: str = "info") -> None:
    request_id = get_request_id()

    log_fn = {
        "debug": logger.debug,
        "info": logger.info,
        "warning": logger.warning,
        "error": logger.error,
    }.get(level, logger.info)

    log_fn("%s [requestId=%s]", message, request_id)

    with sentry_sdk.push_scope() as scope:
        scope.set_tag("request_id", request_id)
        scope.set_extra("request_id", request_id)
        sentry_sdk.capture_message(message, level=level)