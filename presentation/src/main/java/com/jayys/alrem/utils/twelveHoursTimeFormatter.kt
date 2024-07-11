package com.jayys.alrem.utils


fun twelveHoursTimeFormatter(hour: Int) : Pair<String, Int>
{
    var amPmText = "오전 "
    var hourFormatter = hour

    if(hour == 12)
    {
        amPmText = "오후 "
    }
    else if(hour >= 13)
    {
        hourFormatter -= 12
        amPmText = "오후 "
    }

    return Pair(amPmText, hourFormatter)
}