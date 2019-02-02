package jp.co.systena.tigerscave.RPG_Similar.application.model;

import java.util.Random;

public class Job extends Human {

  // 種族値
  private int Base_status = 120;

  // コンストラの引数がないなら剣士
  public Job() {

    super.setName("剣士");

    // 攻撃力を取得し、補正(上方)をかける (120 - 150)
    int attack = super.getOffensive_power();
    double new_attack = attack * 1.25;
    attack = (int)new_attack;
    super.setOffensive_power(attack);

    // HPを取得し、補正(上方)をかける (120 - 150)
    int HP = super.getHit_point();
    double new_HP = HP * 1.25;
    HP = (int)new_HP;
    super.setHit_point(HP);
    super.setMax_hit_point(HP);

    // 素早さを取得し、補正(下方)をかける (72 - 108)
    int speed = super.getSpeed();
    double new_speed = speed * 0.9;
    speed = (int)new_speed;
    super.setSpeed(speed);

    // 総ステータスを計算しランクを決定
    int total_status = super.getHit_point() + super.getOffensive_power() + super.getDefense_power() + super.getSpeed();
    super.setRank(getRank(total_status));

    // 最初の経験値をセット
    super.setExperience_point(0);

    // 最初の最大経験値をセット
    super.setMax_Experience_point(120);

  }

  //コンストラの引数があるならそのジョブになる。
  public Job(String name) {

    super.setName(name);

    // 防御力を取得し、補正(上方)をかける (120 - 150)
    int defense = super.getDefense_power();
    double new_defense = defense * 1.25;
    defense = (int)new_defense;
    super.setDefense_power(defense);;

   // HPを取得し、補正(下方)をかける (120 - 150)
   int HP = super.getHit_point();
   double new_HP = HP * 0.9;
   HP = (int)new_HP;
   super.setHit_point(HP);
   super.setMax_hit_point(HP);

   // 素早さを取得し、補正(上方)をかける (72 - 108)
   int speed = super.getSpeed();
   double new_speed = speed * 1.25;
   speed = (int)new_speed;
   super.setSpeed(speed);

   // 総ステータスを計算しランクを決定
   int total_status = super.getHit_point() + super.getOffensive_power() + super.getDefense_power() + super.getSpeed();
   super.setRank(getRank(total_status));

   // 最初の経験値をセット
   super.setExperience_point(0);

   // 最初の最大経験値をセット
   super.setMax_Experience_point(80);

 }

  // 経験値を追加する
  public void addExperience_point(int Experience_point) {
    // 現在の経験値を取得する。
    int now_Experience_point = super.getExperience_point();
    // 取得経験値を足す
    now_Experience_point = now_Experience_point + Experience_point;
    // 最大経験値を取得する。
    int Max_Experience_point = super.getMax_Experience_point();

    if(Max_Experience_point <= now_Experience_point) {
      // レベルアップ時
      // あふれた経験値を取得し現在経験値とする。
      now_Experience_point = now_Experience_point - Max_Experience_point;
      super.setExperience_point(now_Experience_point);
      // 次回のレベルをセット
      int next_level = super.getLevel() + 1;
      super.setLevel(next_level);
      // 次回の最大経験値をセット
      int next_max_ex_point = (int)(Max_Experience_point + (1.25 * next_level * next_level));
      super.setMax_Experience_point(next_max_ex_point);

      // 各種新しいステータスをセット
      super.setMax_hit_point(new_status_hp(super.getMax_hit_point(),super.getLevel()));
      super.setOffensive_power(new_status_not_hp(super.getOffensive_power(),super.getLevel()));
      super.setDefense_power(new_status_not_hp(super.getDefense_power(),super.getLevel()));
      super.setSpeed(new_status_not_hp(super.getSpeed(),super.getLevel()));

      // レベルアップ時には、HPを回復する
      super.setHit_point(super.getMax_hit_point());

    }else {
      super.setExperience_point(now_Experience_point);
    }
  }


  /****************************
   * レベルアップ時のステータスを返す(HP)
   */
  public int new_status_hp(int hit_point,int level) {
    Random rnd = new Random();
    int new_hit_point = hit_point +(15 * (rnd.nextInt(20)+80)/100) + level;
    return new_hit_point;

  }

  /*****************************
   * レベルアップ時のステータスを返す(HP以外)
   */
  public int new_status_not_hp(int status,int level) {
    Random rnd = new Random();
    int new_status = status + (10 * (rnd.nextInt(20)+80)/100) + level;
    return new_status;
  }

  /*****************************
   * キャラのステータス合計値で
   * Rankを返す
   * @return rank
   */
  public String getRank(int total_status) {

    String rank;

    if ( total_status > 500) {
      rank = "SS";
    }else if ( 485 < total_status && total_status <= 500 ) {
      rank = "S";
    }else if ( 457 < total_status && total_status <= 485 ) {
      rank = "A";
    }else if ( 433 < total_status && total_status <= 457 ) {
      rank = "B";
    }else {
      rank = "C";
    }
    return rank;
  }


  @Override
  public int Attack(double weather_cor) {
    // 攻撃力を取得
    int attack = super.getOffensive_power();

    // 敵に与えるダメージを計算
    double base_damage = attack * this.Base_status * weather_cor / 100;
    int damage = (int)base_damage;

    return damage;
  }

}
