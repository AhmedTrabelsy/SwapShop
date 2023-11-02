import express from 'express';
const app = express();


app.get("/api", (req, res) => {
    res.send("Hello world !")
})


app.listen(8089, () => console.log("listening on port 8089..."));