
from keras_preprocessing.image import ImageDataGenerator, flip_axis, transform_matrix_offset_center
from skimage import exposure
from sklearn.model_selection import train_test_split

import pickle
import gzip
import keras
import numpy as np

# Personal modules
# from src 
import Augmentation


"""===================================LOAD DATA==============================="""


# Unzips .gz file with image data and loads the data
def load_data():
    f = gzip.open('file_out.pkl.gz', 'rb')
    training_data, test_data = pickle.load(f, encoding='latin1')
    f.close()
    return training_data, test_data


"""===================================LOAD DATA WRAPPER==============================="""


# Formats the image data into [120, 120, 1] shape
def load_data_wrapper():
    tr_d, te_d = load_data()

    shape = 120, 120, 1

    training_inputs = [np.reshape(x, shape) for x in tr_d[0]]
    training_out = [np.reshape(x, shape) for x in tr_d[1]]

    test_inputs = [np.reshape(x, shape) for x in te_d[0]]
    test_out = [np.reshape(x, shape) for x in te_d[1]]

    train_set = (np.array(training_inputs), np.array(training_out))
    test_set = (np.array(test_inputs), np.array(test_out))

    return train_set, test_set


"""===================================DATA PREPROCESSING=============================="""


# Performs data normalization and splits the data into training, validation and test
def data_preprocessing(x_train, x_test, augment):
    height = 120
    width = 120

    x_train_in = np.reshape(x_train[0], (x_train[0].shape[0], height, width, 1)).astype('float32')
    x_train_out = np.reshape(x_train[1], (x_train[1].shape[0], height, width, 1)).astype('float32')

    x_test_in = np.reshape(x_test[0], (x_test[0].shape[0], height, width, 1)).astype('float32')
    x_test_out = np.reshape(x_test[1], (x_test[1].shape[0], height, width, 1)).astype('float32')

    if augment > 0:
        x_train_in, x_train_out = Augmentation.data_augmentation(x_train[0], x_train[1], augment)
        x_train_in, x_valid_in, x_train_out, x_valid_out = train_test_split(x_train_in, x_train_out, test_size=0.20)

        x_valid_in = np.reshape(x_valid_in, (x_valid_in.shape[0], height, width, 1)).astype('float32')/255
        x_valid_out = np.reshape(x_valid_out, (x_valid_out.shape[0], height, width, 1)).astype('float32')/255
        validation = (x_valid_in, x_valid_out)
    else:
        valid = 0

    x_train_in = np.reshape(x_train[0], (x_train[0].shape[0], height, width, 1)).astype('float32')/255
    x_train_out = np.reshape(x_train[1], (x_train[1].shape[0], height, width, 1)).astype('float32')/255

    x_test_in = np.reshape(x_test[0], (x_test[0].shape[0], height, width, 1)).astype('float32')/255
    x_test_out = np.reshape(x_test[1], (x_test[1].shape[0], height, width, 1)).astype('float32')/255

    train_set = (x_train_in, x_train_out)
    test_set = (x_test_in, x_test_out)

    valid_set = validation if augment > 0 else valid

    return train_set, test_set, valid_set



