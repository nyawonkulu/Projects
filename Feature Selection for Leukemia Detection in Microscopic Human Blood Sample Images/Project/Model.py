# -*- coding: utf-8 -*-
from keras.layers import Input, MaxPooling2D, UpSampling2D, Cropping2D, ZeroPadding2D, \
    BatchNormalization, Conv2D, Conv2DTranspose
from keras.layers.core import Activation
from keras.models import Model
from keras.regularizers import l2


"""================================CNN AUTOENCODER=========================="""


# Implementation of model
def create_model():
    input_img = Input(shape=(120, 120, 1))

    """======================================================================================================================================"""

    x = Conv2D(32, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False, kernel_initializer='he_normal')(
        input_img)
    x = BatchNormalization(momentum=0.1)(x)
    x = MaxPooling2D((2, 2), padding='same')(x)
    x = Activation('relu')(x)

    """==========================================================ENCODER====================================================================="""

    x = Conv2D(64, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False, kernel_initializer='he_normal')(
        x)
    x = BatchNormalization(momentum=0.1)(x)
    x = MaxPooling2D((2, 2), padding='same')(x)
    x = Activation('relu')(x)

    """======================================================================================================================================"""

    x = ZeroPadding2D(padding=(1, 1), data_format=None)(x)

    x = Conv2D(128, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
               kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = MaxPooling2D((2, 2), padding='same')(x)
    x = Activation('relu')(x)

    """======================================================================================================================================"""

    x = Conv2D(256, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
               kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = MaxPooling2D((2, 2), padding='same')(x)
    x = Activation('relu')(x)

    """======================================================BOTTLENECK===================================================================="""

    encoded = Conv2D(1, (3, 3), padding='same', activation='sigmoid')(x)

    """=======================================================DECODER======================================================================"""

    x = Conv2DTranspose(256, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
                        kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = UpSampling2D((2, 2))(x)
    x = Activation('relu')(x)

    """==================================================================================================================================="""

    x = Conv2DTranspose(128, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
                        kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = UpSampling2D((2, 2))(x)
    x = Cropping2D(cropping=((1, 1), (1, 1)))(x)
    x = Activation('relu')(x)

    """======================================================================================================================================"""

    x = Conv2DTranspose(64, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
                        kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = UpSampling2D((2, 2))(x)
    x = Activation('relu')(x)

    """======================================================================================================================================"""

    x = Conv2DTranspose(32, (3, 3), padding='same', kernel_regularizer=l2(1E-4), use_bias=False,
                        kernel_initializer='he_normal')(x)
    x = BatchNormalization(momentum=0.1)(x)
    x = UpSampling2D((2, 2))(x)
    x = Activation('relu')(x)

    """======================================================================================================================================"""

    decoded = Conv2DTranspose(1, (3, 3), activation='sigmoid', padding='same')(x)

    autoencoder = Model(input_img, decoded)

    encoder = Model(input_img, encoded)

    autoencoder.summary()

    return autoencoder, encoder



