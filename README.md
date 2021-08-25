[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# StateLayout

StateLayout can display different state layouts, Content, Loading, Info.

# Usage
```
<com.rounx.statelayout.StateLayout
    android:id="@+id/state_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:state_layout_state="info"
    tools:state_layout_info_layout="@layout/custom_state_layout_info"
    tools:state_layout_loading_layout="@layout/custom_state_layout_loading">
```

```
state_layout.setState(State.CONTENT)

state_layout.setState(State.LOADING)

state_layout.apply {
    setState(State.INFO)
    infoTitle("Failed to load")
    infoMessage("Network connection failed, please try again later")
    infoImage(R.drawable.ic_baseline_cloud_off_24, R.color.state_layout_error_color)
    infoButtonText("Retry") {
        Toast.makeText(this@MainActivity, "Retry", Toast.LENGTH_SHORT).show()
    }
}
```

# Setup
#### build.gradle
```
repositories {
    mavenCentral()
}
```
```
dependencies {
    implementation 'com.rounx.android:statelayout:1.0.0'
}
```

# Screenshots

![Screenshot](/screenshots/one.png)
![Screenshot](/screenshots/two.png)
![Screenshot](/screenshots/three.png)

License
-------

Copyright 2020 Rounx

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
