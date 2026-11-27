from APC import app
from flask import request, render_template, redirect, url_for, session, abort, flash

@app.route("/")
def index():
    return render_template("index.html", user=session.get("user"))

@app.route("/animation")
def animation():
    return render_template("animation.html", user=session.get("user"))


@app.route("/login", methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        firstname = request.form.get('firstname')
        lastname  = request.form.get('lastname')
        role      = request.form.get('role')
        
        if firstname and lastname:
            # Enregistrement des informations utilisateur dans la session
            session['user'] = {
                "firstname": firstname,
                "lastname": lastname,
                "role": role
            }
            return redirect(url_for("admin"))
        else:
            flash("Des champs ne sont pas saisis !", 'error')
            
    return render_template("login.html", user=session.get("user"))

@app.route("/logout")
def logout():
    # Suppression complète des données de la session (Efface le cookie)
    session.clear()
    return redirect(url_for("index"))

@app.route("/admin")
def admin():
    mon_user = session.get("user")
    if mon_user is None:
        abort(403)
    else:
        return render_template("admin.html", user=session.get("user"))


