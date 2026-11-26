from APC import app, db
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import Session

session = None
base = automap_base()

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True, port=8080)