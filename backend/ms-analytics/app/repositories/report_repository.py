import logging
from app.core.database import get_database
from app.schemas.analytics import FullAnalyticsReport

logger = logging.getLogger(__name__)
COLLECTION = "reports"


async def save_report(report: FullAnalyticsReport) -> str:
    try:
        print(">>> Entrando a save_report", flush=True)
        db = get_database()
        print(f">>> DB name: {db.name}", flush=True)
        doc = report.model_dump()
        print(f">>> Insertando documento...", flush=True)
        result = await db[COLLECTION].insert_one(doc)
        print(f">>> Insertado con id: {result.inserted_id}", flush=True)
        return str(result.inserted_id)
    except Exception as e:
        print(f">>> ERROR: {e}", flush=True)
        raise


async def get_all_reports(limit: int = 10) -> list[dict]:
    try:
        print(">>> Entrando a get_all_reports", flush=True)
        db = get_database()
        cursor = db[COLLECTION].find({}, {"_id": 0}).sort("generatedAt", -1).limit(limit)
        return await cursor.to_list(length=limit)
    except Exception as e:
        print(f">>> ERROR en get_all_reports: {e}", flush=True)
        raise