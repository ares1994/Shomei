<h1 align="center">
<img src="https://drive.google.com/file/d/1Ky19hsREmhFJf3G8ra6KYZrPMJLVxotP/view?usp=sharing" width="280" height="498" alt="Video 1"/>
<img src="https://drive.google.com/file/d/1UR3TP5vOvDgkuuv1690nMZzB0u6BrOKD/view?usp=sharing" width="280" height="498" alt="Screenshot 1"/>
<img src="https://drive.google.com/file/d/1nLzpEgVR19fI8sYN9zUdp03e8iv3bFNH/view?usp=sharing" width="280" height="498" alt="Screenshot 2"/><br/>

    Shomei
</h1>



An implementation of View, providing multiple features and customization options geared to easing the process of collecting hand-drawn signatures in android apps.

**Min SDK:** 14

## Installation

TapTargetView is distributed using [Jitpack](https://jitpack.io/#ares1994/Shomei/).

```groovy
   repositories {
      ....
      ....
        maven { url 'https://jitpack.io' }
   }
   
   dependencies {
         implementation 'com.github.ares1994:Shomei:v0.14-alpha'
   }
```


## Usage

### Simple usage

```kotlin
binding.shomeiView.apply{

    setCanvasColor(Color.parseColor("#ffffff")) //Specify the background color of view. Default is white. Only accepts @ColorInt
    setDrawColor(Color.parseColor("#000000"))   //Specify the color of frames and draw color. Default is black. Only accepts @ColorInt
    setFrameType(ShomeiView.FrameType.AllSides, ShomeiView.Side.Left) // Specify type of Frame you want for your ShomeiView. Default is AllSides. 
                                                                      // Other options are OneSide, DirectOppositesTopBottom, DirectOppositesLeftRight, None. 
                                                                      // Also specify side if frameType selected is OneSide. Options are Left, Right, Top, Bottom
    
    
     getImageFile(externalCacheDir,,Bitmap.CompressFormat.JPEG,90f)   // Get the image file for your view. The only compulsory argument to be specified is the directory. 
                                                                      // Default Compression Format is PNG. Default rotation angle is 0f

}               
 
```

*Tip: If you intend to share the retrieved image file outside the app. Use a FileProvider to get the URI instead of the toUri() extension function
## License

    Copyright 2016 Keepsafe Software Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
