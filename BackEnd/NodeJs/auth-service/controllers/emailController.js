const sgMail = require('@sendgrid/mail')

function sendEmail(email, username) {
    const API_KEY = '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';

    sgMail.setApiKey(API_KEY)

    const messge = {
        to: email,
        from: 'azettt532@gmail.com',
        subject: 'Welcome to Swap Shop!',
        html: `<!DOCTYPE html>
        <html>
        <head>
          <style>
            /* CSS styles for the email */
            body {
              font-family: Arial, sans-serif;
              background-color: #f5f5f5;
              padding: 20px;
            }
            .container {
              max-width: 600px;
              margin: 0 auto;
              background-color: #ffffff;
              padding: 30px;
              border-radius: 5px;
            }
            .logo {
              text-align: center;
              margin-bottom: 20px;
            }
            .button {
              display: inline-block;
              background-color: #4caf50;
              color: #ffffff;
              text-decoration: none;
              padding: 10px 20px;
              border-radius: 4px;
            }
            .button:hover {
              background-color: #45a049;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <div class="logo">
              <img src="http://34.199.239.78:8888/NEWS-SERVICE/lastUpload" alt="Swap Shop Logo">
            </div>
            <h2>Welcome to Swap Shop!</h2>
            <p>Dear ${username},</p>
            <p>Thank you for joining Swap Shop, your go-to platform for buying and selling products. Get ready to explore a world of endless possibilities!</p>
            <p>With Swap Shop, you can:</p>
            <ul>
              <li>Discover unique items listed by sellers from around the world.</li>
              <li>Showcase your own products and reach a wide audience of potential buyers.</li>
              <li>Connect with like-minded individuals who share your passion for buying and selling.</li>
            </ul>
            <p>Download the Swap Shop mobile app for Android:</p>
            <p>
              <a class="button" href="http://34.199.239.78:1000">Download App</a>
            </p>
            <p>Or access the Swap Shop web application:</p>
            <p>
              <a class="button" href="https://www.swapshop.com">Visit Website</a>
            </p>
            <p>Start exploring and making great deals today!</p>
            <p>Happy swapping!</p>
            <p>The Swap Shop Team</p>
          </div>
        </body>
        </html>`
    }

    sgMail.send(messge).then((response) => console.log('Email sent...')).catch((error) => console.log('Error sending email ! ' + error))
}
