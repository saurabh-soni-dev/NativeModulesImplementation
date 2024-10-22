import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import CameraModule from './src/cameraModule/CameraModule';
import DeviceInfoModule from './src/deviceInfoModule/DeviceInfoModule';
import HomeScreen from './src/homeScreen/HomeScreen';

const App = () => {
  const Stack = createNativeStackNavigator();
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="HomeScreen">
        <Stack.Screen name="HomeScreen" component={HomeScreen} />
        <Stack.Screen name="CameraModule" component={CameraModule} />
        <Stack.Screen name="DeviceInfoModule" component={DeviceInfoModule} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
