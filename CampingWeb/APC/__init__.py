from flask import Flask
from flask_sqlalchemy import SQLAlchemy
app = Flask(__name__)
app.config['SECRET_KEY'] = 'ma_cle_secrete_camping'

app.config["SQLALCHEMY_DATABASE_URI"] = "mysql+pymysql://Himation:Camping@Anee_2027@172.17.0.18/apCamping"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db = SQLAlchemy(app)

from APC import routes, models