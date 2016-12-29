# Nameles
## First ever open source solution for detecting invalid traffic (ad fraud)

### Overview

Nameles is an entropy based open source ad fraud detection solution that detects traffic anomalies agnostically across desktop and mobile. It has been proven[1] to deliver excellent results in detecting banner, video and app fraud at large scale with minimal Total-Cost-of-Ownership.

Learn more at http://nameles.org

### Log File Format 

Nameles has been built and tested with various common bid / log file formats, and can be easily setup to work with others. The codes that you find in the repo will work with the following format out-of-the-box:

    118.240.2.92,Yt76Duc954PulBCOCoHwCOBVb64=,Ad-Stir RTB Engine 3.0,jp.ne.ibis.ibispaintx.app,jp.ne.ibis.ibispaintx.app,2016-12-01 14:25:04
    153.159.66.191,pa2kC+53FnhGSn3usjFRi+a+lsY=,Ad-Stir RTB Engine 3.0,com.web_loom.bbs_matome2,com.web_loom.bbs_matome2,2016-12-01 14:37:05
    213.143.48.47,/pzTEIMFcs95GA4EkIJFC8Zqkes=,,com.greenlionsoft.free.madrid,madrid.free.greenlionsoft.com,2016-12-01 14:38:41
    60.138.226.237,FlBh78s6qVs8+JKKDp4IQo4obgM=,Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko,anonymous.xrost.com,,2016-12-01 14:27:21
    175.158.200.140,,be2/1.0,en.softonic.com,en.softonic.com,2016-12-01 14:30:07
    219.100.131.123,DCSPgRVsyW7YOKaZMXKb2xUIO2E=,be2/1.0,chobirich.us,chobirich.us,2016-12-01 14:15:07
    112.153.74.90,vtETnuShjwD0MAQa9S1QfDLYTwM=,spray-can/1.3.3.e,com.cmcm.locker,,2016-12-01 14:44:06
    77.119.128.37,T/aqqCBAr8MuE+kdcMYhHxV7iew=,spray-can/1.3.3.e,com.cleanmaster.mguard,,2016-12-01 14:18:52
    76.198.138,zcqXdwf+7pNrBAK+ksmEEn/G6iI=,YHC/1.0,mg.mail.yahoo.com,,2016-12-01 14:26:27
    107.219.252.241,LF5rUhJzH15Oh9BrtFRRiu7cY+g=,"Mozilla/5.0 (Linux; Android 6.0.1; SM-G900V Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/54.0.2840.85 Mobile Safari/537.36",net.zedge.android,,2016-12-01 14:19:04
