import { useEffect, useState } from "react";

function App() {
  const [tweets, setTweets] = useState([]);
  const [content, setContent] = useState("");

  const getTweets = async () => {
    const res = await fetch("http://localhost:3000/tweet/findByUserId?userId=1");
    const data = await res.json();
    setTweets(data);
  };

  useEffect(() => {
    getTweets();
  }, []);

  const createTweet = async () => {
    if (!content.trim()) return;

    await fetch("http://localhost:3000/tweet", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        content,
        userId: 1,
      }),
    });

    setContent("");
    getTweets();
  };

  const deleteTweet = async (id) => {
    await fetch(`http://localhost:3000/tweet/${id}?userId=1`, {
      method: "DELETE",
    });

    getTweets();
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#121212",
        color: "white",
        padding: "40px",
      }}
    >
      <div style={{ maxWidth: "600px", margin: "auto" }}>
        <h1 style={{ textAlign: "center", fontSize: "42px" }}>
          Twitter Clone
        </h1>

        <textarea
          placeholder="Ne düşünüyorsun?"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          style={{
            width: "100%",
            height: "100px",
            padding: "10px",
            borderRadius: "8px",
            backgroundColor: "#1e1e1e",
            color: "white",
            border: "1px solid #444",
            resize: "none",
          }}
        />

        <button
          onClick={createTweet}
          style={{
            marginTop: "10px",
            width: "100%",
            padding: "10px",
            borderRadius: "8px",
            border: "none",
            backgroundColor: "#1d9bf0",
            color: "white",
            fontWeight: "bold",
            cursor: "pointer",
          }}
        >
          Tweet At
        </button>

        <hr style={{ margin: "25px 0", borderColor: "#333" }} />

        <h2 style={{ textAlign: "center" }}>Tweet List</h2>

        {tweets.map((tweet) => (
          <div
            key={tweet.id}
            style={{
              border: "1px solid #444",
              padding: "15px",
              marginBottom: "12px",
              borderRadius: "10px",
              backgroundColor: "#1e1e1e",
            }}
          >
            <p style={{ fontSize: "18px" }}>{tweet.content}</p>

            <small style={{ color: "#aaa" }}>
              @{tweet.user?.username}
            </small>

            <br />

            <button
              onClick={() => deleteTweet(tweet.id)}
              style={{
                marginTop: "10px",
                backgroundColor: "#e0245e",
                color: "white",
                border: "none",
                padding: "6px 12px",
                borderRadius: "6px",
                cursor: "pointer",
              }}
            >
              Sil
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;