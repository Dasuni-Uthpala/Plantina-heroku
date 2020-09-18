import os
from flask import Flask, jsonify, request
from flask_restful import Api, Resource
import pickle


app = Flask(__name__)
api = Api(app)

model = pickle.load(open("soil_model.pkl","rb"))

@app.route("/")
def home():
    return "Welcome to Plantina API!"

@app.route("/predict/<data>", methods=["POST"])
def post(data):
        posted_data=request.get_json(force=True)
        ph_value = posted_data['pH']
        temperature_value = posted_data['Temperature']

        data=([[ph_value,temperature_value]])
        prediction = model.predict(data)[0]

        return jsonify({
                'prediction':
                prediction
            })


#api.add_resource(MakePrediction, '/predict', methods=["POST","GET"])

if __name__ == '__main__':
        app.run()
