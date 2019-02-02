package jp.co.systena.tigerscave.RPG_Similar.application.model;

public abstract class Enemy {

  // HP
  private int enemy_hit_point;

  // 攻撃力
  private int enemy_Offensive_power;

  // 防御力
  private int enemy_Defense_power;

  // 素早さ
  private int enemy_Speed;

  // レベル
  private int enemy_Level;

  public Enemy() {
    this.enemy_hit_point       = 120;
    this.enemy_Offensive_power = 70;
    this.enemy_Defense_power   = 50;
    this.enemy_Speed           = 60;
  }


  /******************************
   * 各種ステータスをセットする。
   * @param
   */
  // HPをセットする。
  public void setEnemyHP(int hit_point) {
    this.enemy_hit_point = hit_point;
  }

  // 攻撃力をセットする。
  public void setEnemyAttack(int Attack) {
    this.enemy_Offensive_power = Attack;
  }

  // 防御力をセットする。
  public void setEnemyDefence(int defence) {
    this.enemy_Defense_power = defence;
  }

  // 素早さをセットする。
  public void setEnemySpeed(int speed) {
    this.enemy_Speed = speed;
  }

  public void setEnemyLevel(int level) {
    this.enemy_Level = level;
  }

  /*************************
   * 各種ステータスを返す
   * @return
   */
  // HPを返す
  public int getEnemyHP() {
    return this.enemy_hit_point;
  }

  // 攻撃力を返す
  public int getAttack() {
    return this.enemy_Offensive_power;
  }

  // 防御力を返す
  public int getDefence() {
    return this.enemy_Defense_power;
  }

  // 素早さを返す
  public int getSpeed() {
    return this.enemy_Speed;
  }

  // レベルを返す
  public int getEnemyLevel() {
    return this.enemy_Level;
  }

  /*********************
   * 攻撃をする
   * 計算方法などは、各種クラスで設定
   */
  abstract int Attack(double weater_cor);

}