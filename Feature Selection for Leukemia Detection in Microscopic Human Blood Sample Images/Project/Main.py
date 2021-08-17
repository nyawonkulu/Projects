
# Personal modules
# from src 
import Model, Training


def aug_size():
    print("\n")
    val = int(input("ENTER AUGMENTATION SIZE: "))
    batch = int(input("ENTER BATCH SIZE: "))
    epochs = int(input("ENTER EPOCHS: "))
    return val, batch, epochs


def main():
    autoencoder, encoder = Model.create_model()
    val, batch, epochs = aug_size()
    x_test, decoded_imgs, encoded_img = Training.training(autoencoder, encoder, val, batch, epochs)
    Training.display(x_test, decoded_imgs, encoded_img)


if __name__ == "__main__":
    main()
