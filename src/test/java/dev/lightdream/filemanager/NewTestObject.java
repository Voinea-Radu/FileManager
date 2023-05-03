package dev.lightdream.filemanager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Saveable(directory = "test", fileName = "new_test_2.json")
public class NewTestObject {

    public int data1;
    public String data2;

}
