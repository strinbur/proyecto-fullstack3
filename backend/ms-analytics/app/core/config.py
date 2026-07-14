from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    data_aggregation_url: str = "http://localhost:8085"
    jwt_secret_key: str = "secret"
    jwt_algorithm: str = "HS256"
    critical_stock_threshold: int = 5
    mongodb_url: str = "mongodb://localhost:27017"
    mongodb_database: str = "ms-analytics"
    sentry_dsn: str = ""

    class Config:
        env_file = ".env"


settings = Settings()