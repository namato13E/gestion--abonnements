from APC import app, db
from APC.models import *
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import Session

session = None
base = automap_base()
#Matos = None

def setup_database():
    global Matos
    global session
    with app.app_context():
        session = Session(db.engine)
        base.prepare(autoload_with=db.engine)
        #Matos = base.classes.materiels_legacy
        print("Base de données préparée et Matos chargé.")

setup_database()

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True, port=8080)