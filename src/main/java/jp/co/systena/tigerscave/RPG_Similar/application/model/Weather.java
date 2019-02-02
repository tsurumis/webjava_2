package jp.co.systena.tigerscave.RPG_Similar.application.model;

public class Weather extends Tempestas{

  // 引数がある場合は、引数の設定を採用する
  public Weather(String weather_name,String yuuri_name, String huri_name) {

    super.setWeather_name(weather_name);
    super.setYuuri_name(yuuri_name);
    super.setHuri_name(huri_name);

  }

  // 引数がなければ親の初期値を採用する。
  public Weather() {

  }

  @Override
  public int recover(double weater_cor) {

    int recover_cor = 1;

    return recover_cor;
  }

}
