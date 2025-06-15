package com.trenical.client.session;

import com.trenical.grpc.Notifica;

public interface NotificaListener {
    void onNuovaNotifica(Notifica n);
}
