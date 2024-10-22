import {View, Text, TouchableOpacity} from 'react-native';
import React, {FC} from 'react';
import {useNavigation} from '@react-navigation/native';

const HomeScreen: FC = () => {
  const navigation = useNavigation();
  const navigateToModule = (index: number) => {
    switch (index) {
      case 1:
        navigation.navigate('CameraModule');
        break;
      case 2:
        navigation.navigate('DeviceInfoModule');
        break;
      default:
        break;
    }
  };

  return (
    <View style={{flex: 1, padding: 10}}>
      <TouchableOpacity
        style={{backgroundColor: 'lightgray', padding: 10, marginBottom: 10}}
        onPress={() => navigateToModule(1)}>
        <Text style={{fontSize: 16, color: 'black'}}>Camera Native Module</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={{backgroundColor: 'lightgray', padding: 10, marginBottom: 10}}
        onPress={() => navigateToModule(2)}>
        <Text style={{fontSize: 16, color: 'black'}}>Device Info Native Module</Text>
      </TouchableOpacity>
    </View>
  );
};

export default HomeScreen;
