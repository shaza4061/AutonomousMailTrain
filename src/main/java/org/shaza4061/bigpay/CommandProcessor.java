package org.shaza4061.bigpay;

import org.shaza4061.bigpay.manager.NetworkManager;
import org.shaza4061.bigpay.model.Node;

import java.util.List;

public interface CommandProcessor {
    NetworkManager process(List<String> commands);
}
