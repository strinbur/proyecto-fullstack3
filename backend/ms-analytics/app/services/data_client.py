import httpx
from fastapi import HTTPException, status

from app.core.config import settings
from app.schemas.analytics import CombinedDataset


async def fetch_dataset(token: str) -> CombinedDataset:
    """
    Llama a ms-data-aggregation /dataset pasando tambien el JWT del usuario.
    """
    url = f"{settings.data_aggregation_url}/dataset"
    headers = {"Authorization": f"Bearer {token}"}

    async with httpx.AsyncClient(timeout=10.0) as client:
        try:
            response = await client.get(url, headers=headers)
        except httpx.ConnectError:
            raise HTTPException(
                status_code=status.HTTP_503_SERVICE_UNAVAILABLE,
                detail="No se pudo conectar con ms-data-aggregation",
            )
        except httpx.TimeoutException:
            raise HTTPException(
                status_code=status.HTTP_504_GATEWAY_TIMEOUT,
                detail="ms-data-aggregation tardó demasiado en responder",
            )

    if response.status_code == 401:
        raise HTTPException(status_code=401, detail="Token rechazado por ms-data-aggregation")
    if response.status_code != 200:
        raise HTTPException(
            status_code=status.HTTP_502_BAD_GATEWAY,
            detail=f"ms-data-aggregation respondió con {response.status_code}",
        )

    return CombinedDataset.model_validate(response.json())
