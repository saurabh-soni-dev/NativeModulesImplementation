import {
  View,
  Text,
  PermissionsAndroid,
  NativeModules,
  Image,
  TouchableOpacity,
} from 'react-native';
import React, {FC, useState} from 'react';

const CameraModule: FC = () => {
  const [imageUri, setImageUri] = useState(null);
  const {CameraModule} = NativeModules;

  const openCamera = async () => {
    try {
      // Request camera permission
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.CAMERA,
        {
          title: 'Camera Permission',
          message: 'App needs access to your camera',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        },
      );

      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        // Call the native module to open the camera
        const result = await CameraModule.openCamera();
        if (result.uri) {
          setImageUri(result.uri); // Set the captured image URI to the state
          console.log('Image URI: ', result);
        }
      } else {
        console.log('Camera permission denied');
      }
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <View
      style={{
        flex: 1,
        margin: 20,
        justifyContent: 'center',
        alignItems: 'center',
      }}>
      <TouchableOpacity
        style={{
          padding: 10,
          backgroundColor: 'blue',
          alignItems: 'center',
          marginBottom: 20,
        }}
        onPress={openCamera}>
        <Text style={{fontSize: 22, color: 'white'}}>Open Camera</Text>
      </TouchableOpacity>

      {imageUri && (
        <Image
          source={{uri: imageUri}}
          style={{width: 300, height: 500, resizeMode: 'contain'}}
        />
      )}
    </View>
  );
};

export default CameraModule;
