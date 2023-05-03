package dev.lightdream.filemanager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Saveable(directory = "test", fileName = "new_test_3")
public class NewTestObjectNoExtension {

    public int data1;
    public String data2;

}
