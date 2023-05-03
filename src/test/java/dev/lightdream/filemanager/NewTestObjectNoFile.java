package dev.lightdream.filemanager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Saveable(directory = "test")
public class NewTestObjectNoFile {

    public int data1;
    public String data2;

}
