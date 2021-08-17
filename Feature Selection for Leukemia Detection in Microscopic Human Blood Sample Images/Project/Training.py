import numpy as np
from keras.optimizers import SGD, Adam
from keras.callbacks import EarlyStopping, TensorBoard
import matplotlib.pyplot as plt
from random import seed, randint, random

# Personal modules
# from src
import Dataset_loader

"""================================TRAINING=========================="""


def training(autoencoder, encoder, augment, batch, epoch):
    def aug_data(value):
        if value == 0:
            return test_set[0], test_set[1]
        else:
            return valid_set[0], valid_set[1]

    adam = Adam(lr=0.001, beta_1=0.9, beta_2=0.999, amsgrad=False)

    autoencoder.compile(optimizer=adam, loss='binary_crossentropy', metrics=['accuracy', 'mean_squared_error'])

    x_train, x_test = Dataset_loader.load_data_wrapper()
    train_set, test_set, valid_set = Dataset_loader.data_preprocessing(x_train, x_test, augment)

    early = EarlyStopping(
        monitor='val_loss',
        min_delta=0,
        patience=10,
        mode='min'
    )

    autoencoder.fit(train_set[0], train_set[1],
                    epochs=epoch,
                    batch_size=batch,
                    shuffle=True,
                    verbose=1,
                    validation_data=(aug_data(augment)),
                    callbacks=[early]
                    )
    encoded_img = encoder.predict(test_set[0])
    decoded_imgs = autoencoder.predict(test_set[0])

    results = autoencoder.evaluate(test_set[0], test_set[1], batch_size=1)
    print("\nTEST LOSS, TEST ACC: ", results)

    return x_test, decoded_imgs, encoded_img


"""====================================DENOISING=============================="""


def denoising(train_set, valid_set, test_set):
    noise_factor = 0.5

    noise = noise_factor * np.random.normal(loc=0.5, scale=0.5, size=train_set[0].shape)
    train_noisy = train_set[0] + noise
    train_noisy = np.clip(train_noisy, 0., 1.)

    noise = noise_factor * np.random.normal(loc=0.5, scale=0.5, size=valid_set[0].shape)
    valid_noisy = valid_set[0] + noise
    valid_noisy = np.clip(valid_noisy, 0., 1.)

    noise = noise_factor * np.random.normal(loc=0.5, scale=0.5, size=test_set[0].shape)
    test_noisy = test_set[0] + noise
    test_noisy = np.clip(test_noisy, 0., 1.)

    return train_noisy, valid_noisy, test_noisy


"""====================================DISPLAY=============================="""


def display(x_test, decoded_imgs, encoded_imgs):
    shape = (120, 120)

    n = 20  # how many digits we will display

    plt.figure(figsize=(20, 4))
    for i in range(n):
        # display original
        ax = plt.subplot(2, n, i + 1)
        plt.imshow(x_test[0][i].reshape(shape))
        ax.get_xaxis().set_visible(False)
        ax.get_yaxis().set_visible(False)

        # display reconstruction
        ax = plt.subplot(2, n, i + 1 + n)
        plt.imshow(decoded_imgs[i].reshape(shape))
        ax.get_xaxis().set_visible(False)
        ax.get_yaxis().set_visible(False)

    plt.show()
