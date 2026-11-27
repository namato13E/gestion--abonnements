from APC import app
from flask import render_template, session

@app.errorhandler(403)
def forbidden(error):
    return render_template("403.html", title="403 Forbidden"), 403

@app.errorhandler(404)
def not_found(error):
    return render_template("404.html", title="404 Not Found"), 404
