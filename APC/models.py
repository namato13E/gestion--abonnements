from APC import db

class Emplacement(db.Model):
    __tablename__ = 'emplacement'
    
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50), unique=True, nullable=False)
    type_hebergement = db.Column(db.String(50), nullable=False)
    prix_par_nuit = db.Column(db.Float, nullable=False)
    reservations = db.relationship('Reservation', backref='emplacement', lazy=True)


class Reservation(db.Model):
    __tablename__ = 'reservation'
    
    id = db.Column(db.Integer, primary_key=True)
    date_debut=db.Column(db.Date, nullable=False)
    date_fin=db.Column(db.Date, nullable=False)
    client_id = db.Column(db.Integer, db.ForeignKey('client.id'), nullable=False)
    emplacement_id = db.Column(db.Integer, db.ForeignKey('emplacement.id'), nullable=False)
   
class Client(db.Model):
    __tablename__ = 'client'
    
    id = db.Column(db.Integer, primary_key=True)
    nom=db.Column(db.String(50), nullable=False)
    email=db.Column(db.String(50), nullable=False)
    reservations = db.relationship('Reservation', backref='client', lazy=True)
