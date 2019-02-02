package jp.co.systena.tigerscave.RPG_Similar.application.model;

import java.util.Random;

public abstract class Human {
  // 名前
  private String name;
  // HP
  private int hit_point;
  // 最大HP
  private int Max_hit_point;
  // 攻撃力
  private int Offensive_power;
  // 防御力
  private int Defense_power;
  // 回復力
  private int heal_power;
  // 素早さ
  private int speed;
  // レベル
  private int level;
  // ランク
  private String rank;
  // 現在の経験値(敵キャラでは倒すともらえる経験値とする)
  private int Experience_point;
  // 最大経験値
  private int Max_Experience_point;

  public Human() {

    // キャラを作成時にランダムでステータスを生成
    // ステータス生成後、各クラスでステータスに補正をかける。
    Random rnd = new Random();
    this.hit_point       = rnd.nextInt(40) + 80;
    this.Offensive_power = rnd.nextInt(40) + 80;
    this.Defense_power   = rnd.nextInt(40) + 80;
    this.heal_power      = rnd.nextInt(40) + 80;
    this.speed           = rnd.nextInt(40) + 80;
    this.level           = 1;
  }

  /*****************
   * 各種ステータスをセットする
   * @return
   *****************/
  // 名前をセット
  public void setName(String name) {
    this.name = name;
  }

  // HPをセット
  public void setHit_point(int hit_point) {
    this.hit_point = hit_point;
  }

  // 最大HPをセット
  public void setMax_hit_point(int Max_hit_point) {
    this.Max_hit_point = Max_hit_point;
  }

  // 攻撃力をセット
  public void setOffensive_power(int attack) {
    this.Offensive_power = attack;
  }

  // 防御力をセット
  public void setDefense_power(int Defense_power) {
    this.Defense_power = Defense_power;
  }

  // 回復力をセット
  public void setHeal_power(int heal_power) {
    this.heal_power = heal_power;
  }

  // 素早さをセット
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  // レベルをセット
  public void setLevel(int level) {
    this.level = level;
  }

  // ランクをセット
  public void setRank(String rank) {
    this.rank = rank;
  }

  // 経験値をセット
  public void setExperience_point(int Experience_point) {
    this.Experience_point = Experience_point;
  }

  // 最大経験値をセット
  public void setMax_Experience_point(int Max_Experience_point) {
    this.Max_Experience_point = Max_Experience_point;
  }

  /*****************
   * 各種ステータスを返す
   * @return
   *****************/
  // 名前を返す
  public String getName() {
    return this.name;
  }

  // HPを返す
  public int getHit_point() {
    return this.hit_point;
  }

  // 最大HPを返す
  public int getMax_hit_point() {
    return this.Max_hit_point;
  }

  // 攻撃力を返す
  public int getOffensive_power() {
    return this.Offensive_power;
  }

  // 防御力を返す
  public int getDefense_power() {
    return this.Defense_power;
  }

  // 回復力を返す
  public int getHeal_power() {
    return this.heal_power;
  }

  // 素早さを返す
  public int getSpeed() {
    return this.speed;
  }

  // レベルを返す
  public int getLevel() {
    return this.level;
  }

  // ランクを返す
  public String getRank() {
    return this.rank;
  }

  //経験値を返す
  public int getExperience_point() {
    return this.Experience_point;
  }

  // 最大経験値を返す
  public int getMax_Experience_point() {
    return this.Max_Experience_point;
  }

  /*********************
   * 攻撃をする
   * 計算方法などは、各種クラスで設定
   */
  abstract int Attack(double weater_cor);

}
