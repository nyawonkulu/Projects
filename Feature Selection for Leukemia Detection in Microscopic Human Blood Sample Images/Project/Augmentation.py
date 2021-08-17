
import keras
import numpy as np
from keras_preprocessing.image import ImageDataGenerator, flip_axis, transform_matrix_offset_center
from skimage import exposure


"""===================================DATA AUGMENTATION==============================="""


# Transformations
def transformations(x):
    contrast_stretching = True
    adaptive_equalization = False
    histogram_equalization = False

    if contrast_stretching:  
        if np.random.random() < 0.5:  
            p2, p98 = np.percentile(x, (2, 98))  
            x = exposure.rescale_intensity(x, in_range=(p2, p98))  

    if adaptive_equalization:  
        if np.random.random() < 0.5:  
            x = exposure.equalize_adapthist(x, clip_limit=0.03)  

    if histogram_equalization:  
        if np.random.random() < 0.5:  
            x = exposure.equalize_hist(x)
    return x


# Data augmentation method
def data_augmentation(x_train_in, x_train_out, augment_size):
    def random_transform(x):
        return transformations(x)

    
    print("\nGENERATING DATA...")
    image_generator = ImageDataGenerator(        
        rotation_range=15,
        shear_range=0.1,
        zoom_range=0.08,
        width_shift_range=0.1,
        height_shift_range=0.1,
        horizontal_flip=True,
        vertical_flip=True,
        fill_mode='nearest',        
    )

    # fit data for zca whitening
    image_generator.fit(x_train_in, augment=True)
    # get transformed images
    randidx = np.random.randint(x_train_in.shape[0], size=augment_size)
    x_augmented = x_train_in[randidx].copy()
    y_augmented = x_train_out[randidx].copy()
    x_augmented = image_generator.flow(x_augmented, np.zeros(augment_size),
                                       batch_size=augment_size, shuffle=True).next()[0]
    # append augmented data to trainset
    x_train = np.concatenate((x_train_in, x_augmented))
    y_train = np.concatenate((x_train_out, y_augmented))
    print("DONE AUGMENTING...\n")
    return x_train, y_train
