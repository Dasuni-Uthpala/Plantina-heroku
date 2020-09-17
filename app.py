from flask import Flask, request, render_template
import pickle
import numpy as np
from flask_restful import Api,Resource
from flask_cors import CORS
from flask import jsonify
import pandas as pd

app = Flask(__name__)   
CORS(app)
API_NAME = Api(app)
app.config['SECRET_KEY'] = 'disable the web security'
app.config['CORS_HEADERS'] = 'Content-Type'

@app.route("/")
def home():
    return "Welcome to Plantina API!"
class MakePrediction(Resource):
    def get(self):
        info = request.args.getlist('vizdata[]')
        ph_value = int(info[0])
        temperature_value = float(info[1])
        
        model = load_model()
        data = ([[ph_value, temperature_value]])
        col = ['pH', 'Temperature']
        data = np.array(data)
        data = pd.DataFrame(data,columns = col)
        data = data_scaling(data)
        prediction = model.predict(data)
        
        return {'prediction':prediction}




API_NAME.add_resource(MakePrediction, '/predict/result', methods=['POST', 'GET'])

def load_model():
    loaded_model = pickle.load(open('soil_model.pkl', 'rb'))
    return loaded_model


def data_scaling(df):

    cont_data = df.values
    m = open('scaled_model.pkl', 'rb')
    model = pickle.load(m)
    cont_data = model.transform(cont_data)
    return cont_data


if __name__ == '__main__':
        app.run()