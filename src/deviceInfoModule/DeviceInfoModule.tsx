import {View, Text, NativeModules} from 'react-native';
import React, {FC, useEffect, useState} from 'react';

const DeviceInfoModule: FC = () => {
  const {DeviceInfoModule} = NativeModules;

  const [deviceInfo, setDeviceInfo] = useState({});

  useEffect(() => {
    getDeviceInfo();
  }, []);

  const getDeviceInfo = async () => {
    try {
      const deviceInfo = await DeviceInfoModule.getDeviceInfo();
      setDeviceInfo(deviceInfo);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <View style={{flex: 1, padding: 10}}>
      {Object.keys(deviceInfo).map(key => (
        <Text
          key={key}
          style={{fontSize: 16, marginBottom: 5, textTransform: 'capitalize', color:'black'}}>
          {key}: {deviceInfo[key]}
        </Text>
      ))}
    </View>
  );
};

export default DeviceInfoModule;
