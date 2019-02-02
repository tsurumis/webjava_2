package jp.co.systena.tigerscave.RPG_Similar.application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Wimp extends Human{

  private int Base_status = 70;

  public Wimp(int level) {
    // 名前の設定
    super.setName("ザコ");

    // 敵を作成時、味方のレベルにより変動させる。
    int enemy_level = selectEnemyLevel(level);
    super.setLevel(enemy_level);

    // 敵レベル設定後に各種ステータスを設定する。
    Random rnd = new Random();
    // 敵HPの設定
    int wimp_hit_point = (int)(70 + (1.75 * enemy_level * (enemy_level - 1) ) ) * (rnd.nextInt(24) + 88)/100;
    super.setHit_point(wimp_hit_point);
    super.setMax_hit_point(wimp_hit_point);
    // 敵攻撃力の設定
    int wimp_Offensive_power = (int)(80 + (1.5 * enemy_level * (enemy_level - 1) ) ) * ( rnd.nextInt(24) + 76) / 100;
    super.setOffensive_power(wimp_Offensive_power);
    // 敵防御力の設定
    int wimp_Defense_power = (int)(70 + ( 1.3 * enemy_level * (enemy_level - 1) ) ) * (rnd.nextInt(24) + 76) / 100;
    super.setDefense_power(wimp_Defense_power);
    // 敵素早さの設定
    int wimp_Speed = (int)(70 + ( 1.25 * enemy_level * (enemy_level - 1) ) ) * (rnd.nextInt(24) + 76) / 100;
    super.setSpeed(wimp_Speed);
    // 倒すともらえる経験値をセット
    int Ex_point = (int)(50 + (0.5 * enemy_level * (enemy_level - 1) ) );
    super.setExperience_point(Ex_point);
  }


  /************************
   * 敵のレベルを返すメソッド
   * 味方のレベルで変動する。
   * @param level
   * @return
   */
  public int selectEnemyLevel(int level) {

    ArrayList<Integer> enemy_level_list = new ArrayList<Integer>();

    for (int i = 0 ; i <= 13 ; i++) {
      enemy_level_list.add(level);
    }

    for (int i = 0 ; i <= 2 ; i++) {
      int level_pulus_1 = checkLevel(level + 1);
      int level_minus_1 = checkLevel(level - 1);
      enemy_level_list.add(level_pulus_1);
      enemy_level_list.add(level_minus_1);
    }

    for (int i = 0 ; i <= 2 ; i++) {
      int level_pulus_2 = checkLevel(level + 2);
      int level_minus_2 = checkLevel(level - 2);
      enemy_level_list.add(level_pulus_2);
      enemy_level_list.add(level_minus_2);
    }

    for (int i = 0 ; i <= 2 ; i++) {
      int level_pulus_3 = checkLevel(level + 3);
      int level_minus_3 = checkLevel(level - 3);
      enemy_level_list.add(level_pulus_3);
      enemy_level_list.add(level_minus_3);
    }

    if (level >= 5) {
      for (int i = 0 ; i <= 1 ; i++) {
        int level_pulus_4 = checkLevel(level + 4);
        int level_minus_4 = checkLevel(level - 4);
        enemy_level_list.add(level_pulus_4);
        enemy_level_list.add(level_minus_4);
      }
    }

    if (level > 8) {
      for (int i = 0 ; i <= 1 ; i++) {
        int level_pulus_5 = checkLevel(level + 5);
        int level_minus_5 = checkLevel(level - 5);
        enemy_level_list.add(level_pulus_5);
        enemy_level_list.add(level_minus_5);
      }
    }

    // 敵レベル配列をシャッフルし、先頭の数値を敵レベルとする。
    Collections.shuffle(enemy_level_list);
    int enemy_level = enemy_level_list.get(0);

    return enemy_level;

  }

  /********************
   * 引数のレベルが0以下かどうかを判断するメソッド
   * 0以下なら1を返し、それ以外なら引数のレベルを返す
   */
  public int checkLevel(int check_level) {

    int return_level = check_level;

    if (check_level <= 0) {
      return_level = 1;
    }
    return return_level;
  }


  @Override
  public int Attack(double weather_cor) {
    // 攻撃力を取得
    int attack = super.getOffensive_power();

    // 敵に与えるダメージを計算
    double base_damage = attack * this.Base_status * weather_cor;

    // 難易度によるダメージ補正
    double damage_new = base_damage * 0.75;
    int damage = (int)damage_new;

    return damage;
  }

}
