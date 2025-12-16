```python
# 1. SCRAPER: scrape_shl.py
import requests
from bs4 import BeautifulSoup
import json

URL = "https://example-shl.com/catalog"  
res = requests.get(URL)
soup = BeautifulSoup(res.text, "html.parser")

assessments = []
for card in soup.select(".assessment-card"):
    assessments.append({
        "name": card.select_one("h3").text.strip(),
        "description": card.select_one("p").text.strip(),
        "skills": card.get("data-skills").split(","),
        "level": card.get("data-level")
    })

with open("assessments.json", "w") as f:
    json.dump(assessments, f, indent=2)
```

```python
# 2. VECTOR STORE: vector_store.py
from langchain.vectorstores import FAISS
from langchain.embeddings import OpenAIEmbeddings
import json

with open("assessments.json") as f:
    data = json.load(f)

texts = [f"{d['name']} {d['description']} {','.join(d['skills'])}" for d in data]

embeddings = OpenAIEmbeddings()
vector_db = FAISS.from_texts(texts, embeddings)
vector_db.save_local("shl_faiss")
```

```python
# 3. RAG PIPELINE: rag_engine.py
from langchain.vectorstores import FAISS
from langchain.embeddings import OpenAIEmbeddings
from langchain.llms import OpenAI

embeddings = OpenAIEmbeddings()
db = FAISS.load_local("shl_faiss", embeddings)
llm = OpenAI()

def recommend(query):
    docs = db.similarity_search(query, k=5)
    context = "\n".join([d.page_content for d in docs])

    prompt = f"""
    User Query: {query}
    Assessments:
    {context}

    Recommend best 3 assessments with reasons.
    """
    return llm(prompt)
```

```python
# 4. API: app.py
from fastapi import FastAPI
from rag_engine import recommend

app = FastAPI()

@app.post("/recommend")
def get_recommendation(query: str):
    return {"result": recommend(query)}
```

```python
# 5. EVALUATION: evaluate.py
from rag_engine import recommend

test_queries = [
    "Junior Java developer assessment",
    "Leadership assessment for managers"
]

for q in test_queries:
    print("Query:", q)
    print(recommend(q))
    print("---")
```
