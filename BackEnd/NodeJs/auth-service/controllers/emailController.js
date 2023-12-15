class EmailController {
    static async sendEmail(email, username, type) {
        const API_KEY =
            "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";
        const sgMail = require("@sendgrid/mail");
        sgMail.setApiKey(API_KEY);
        let message = null;
        if (type == "welcome") {
            message = {
                to: email,
                from: "azettt532@gmail.com",
                subject: "Welcome to Swap Shop!",
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
                .logo img {
                    max-width: 200px;
                    height: auto;
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
                  <img src="http://34.199.239.78:8888/NEWS-SERVICE/uploads/emailBanner.png" alt="Swap Shop Logo">
                </div>
                <h2>Welcome to Swap Shop!</h2>
                <p>Dear <b>${username}</b>,</p>
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
            </html>`,
            };
        } else if (type == "login") {
            const loginTime = new Date().toLocaleString();

            message = {
                to: email,
                from: "azettt532@gmail.com",
                subject: "Login Alert - Swap Shop",
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
                  .logo img {
                      max-width: 200px;
                      height: auto;
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
                        <img src="http://34.199.239.78:8888/NEWS-SERVICE/uploads/emailBanner.png" alt="Swap Shop Logo">
                      </div>
                      <h2>Login Alert - Swap Shop</h2>
                      <p>Dear <b>${username}</b>,</p>
                      <p>There was a login attempt detected on your Swap Shop account at ${loginTime}.</p>
                      <p>If you did not initiate this login, please take immediate action to secure your account by following these steps:</p>
                      <ol>
                        <li>Change your Swap Shop account password.</li>
                        <li>Review your account settings and activity for any unauthorized changes or suspicious activity.</li>
                      </ol>
                      <p>If you initiated the login, you can ignore this alert.</p>
                      <p>If you have any concerns or need further assistance, please contact our support team.</p>
                      <p>Thank you for using Swap Shop.</p>
                      <p>Best regards,</p>
                      <p>The Swap Shop Team</p>
                    </div>
                  </body>
                </html>`,
            };
        }

        if (message != null) {
            await sgMail
                .send(message)
                .then((response) => console.log("Email sent..."))
                .catch((error) => console.log("Error sending email ! " + error));
        }
    }
}

module.exports = EmailController;
