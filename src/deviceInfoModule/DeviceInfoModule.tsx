import {View, Text, NativeModules} from 'react-native';
import React, {FC, useEffect} from 'react';

const DeviceInfoModule: FC = () => {
  const {DeviceInfoModule} = NativeModules;

  useEffect(() => {
    getDeviceInfo();
  }, []);

  const getDeviceInfo = async () => {
    try {
      const deviceInfo = await DeviceInfoModule.getDeviceInfo();
      console.log('deviceInfo: ', deviceInfo);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <View>
      <Text>DeviceInfoModule</Text>
    </View>
  );
};

export default DeviceInfoModule;
