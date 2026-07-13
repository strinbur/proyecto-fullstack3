import sentry_sdk

from app.core.config import settings


def init_sentry() -> None:
    
    sentry_sdk.init(
        dsn=settings.sentry_dsn,
        environment="dev",
        traces_sample_rate=1.0,
        send_default_pii=False,
    )