package jp.co.systena.tigerscave.RPG_Similar.application.model;

public abstract class Tempestas {
  // 天候の名前
  String weather_name;

  // 有利ジョブ(or敵)の名前
  String yuuri_name;

  // 有利ジョブ(or敵)の名前
  String huri_name;

  public Tempestas() {
    // 初期値
    this.weather_name = "晴れ";
    this.yuuri_name   = "剣士";
    this.huri_name    = "魔法使い";
  }

  /*********************
   * 引数のジョブ(or敵)の名前で補正率を返す
   */
  public double getWeather_cor(String type_name) {

    double weather_cor;

    if( this.yuuri_name.equals(type_name)) {
      weather_cor = 1.1;
    } else if(this.huri_name.equals(type_name)){
      weather_cor = 0.75;
    } else{
      weather_cor = 1.0;
    }

    return weather_cor;

  }

  /*********************
   * 回復するボタン押下時の制御
   */
  abstract int recover(double weater_cor);

  /*********************
   * 各種値をセット
   */
  public void setWeather_name(String weather_name) {
    this.weather_name = weather_name;
  }

  public void setYuuri_name(String yuuri_name) {
    this.yuuri_name = yuuri_name;
  }

  public void setHuri_name(String huri_name) {
    this.huri_name = huri_name;
  }

  /*********************
   * 各種値を返す
   */

  public String getWeather_name() {
    return this.weather_name;
  }

  public String getYuuri_name() {
    return this.yuuri_name;
  }

  public String getHuri_name() {
    return this.huri_name;
  }

}
