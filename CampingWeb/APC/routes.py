from APC import app, db
from APC.models import *
from flask import render_template, request, flash, redirect, url_for
from run import *

print()
AllMatos=session.query(Matos).all()
for m in AllMatos:
    print(f"======== Présentation de M{m.id_mat} ========\n\nId:"+str(m.id_mat)+"\nCode:"+m.code_inventaire+"\nNom:"+m.designation+"\nEtat:"+m.etat+"\n")
print("====== Fin de la présentation ======\n\n")

@app.route("/")
def index():
    emplacements_db = Emplacement.query.all()
    return render_template("index.html",emplacements_db=emplacements_db)

@app.route("/emplacement")
def emplacements():
    emplacements_db = Emplacement.query.all()
    emplacements=[]
    for emplacement in emplacements_db:
        emplacements.append(emplacement)
    if len(emplacements_db)==0:
        emplacements="Vide"
    return render_template("emplacements.html",emp=None,emplacements_db=emplacements)
@app.route("/emplacement/<int:emp_id>")
def emplacement(emp_id:int):
    emplacements_db = Emplacement.query.all()
    emp=None
    print(f"{emplacements_db}")
    for emplacement in emplacements_db:
        if emplacement.id == emp_id:
            emp=emplacement
    return render_template("emplacements.html", emplacements_db=emplacements_db, emp=emp)

@app.route("/emplacement/ajouter", methods=['GET','POST'])
def ajouter():
    emplacements=[]
    emplacements_db = Emplacement.query.all()
    for emplacement in emplacements_db:
        emplacements.append(emplacement)

    if request.method == 'POST':
        nom=request.form.get("nom")
        type=request.form.get("type")
        prix=request.form.get("prix")

        emp=Emplacement(nom=nom,type_hebergement=type,prix_par_nuit=prix)
        try:
            db.session.add(emp)
            db.session.commit()
            flash("Emplacement ajouté!","success")
        except Exception as e:
            db.session.rollback()
            flash(f"Erreur lors de l'ajout : {e}","danger")
    return render_template("ajouter_emplacement.html", emplacements=emplacements)

@app.route("/emplacement/supprimer/<int:id>", methods=['POST'])
def supprimer(id:int):
    emp = Emplacement.query.get_or_404(id)
    db.session.delete(emp)
    db.session.commit()
    return redirect(url_for("emplacements"))
