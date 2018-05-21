package com.oxgencui.robot;

import java.io.IOException;

public interface Robot2Listener {
	    void onDo(String response);
	    void onFail(IOException e);
}
