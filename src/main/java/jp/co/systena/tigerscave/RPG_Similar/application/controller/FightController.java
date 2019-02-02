package jp.co.systena.tigerscave.RPG_Similar.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.RPG_Similar.application.model.Job;
import jp.co.systena.tigerscave.RPG_Similar.application.model.Weather;
import jp.co.systena.tigerscave.RPG_Similar.application.model.Wimp;

@Controller // Viewあり。Viewを返却するアノテーション
public class FightController {

  @Autowired
  HttpSession session; // セッション管理

  @RequestMapping(value="/Fight" , method = RequestMethod.POST)
  public ModelAndView show(ModelAndView mav) {

    // キャラの取得
    Job fencer = (Job)session.getAttribute("character");

    // キャラのセット
    mav.addObject("fighter",fencer);

    // 敵キャラの取得
    Wimp wimp = (Wimp)session.getAttribute("enemy");

    // 敵キャラ作成フラグの取得
    String create_enemy_flag = (String)session.getAttribute("enemy_create_flag");

    if ( wimp == null && create_enemy_flag == "create") {
      // 敵キャラを取得出来なかった場合、作成
      Wimp new_wimp = new Wimp(fencer.getLevel());
      session.setAttribute("enemy", new_wimp);
      wimp = new_wimp;
      // メッセージの作成
      List<String> message = new ArrayList<String>();
      message.add("てき　が　あらわれた！");
      mav.addObject("message", message);

      // たたかうコマンドの表示
      session.setAttribute("battle_status", "battle");
    }else if(create_enemy_flag != "create"){
      // メニューボタンの表示
      session.setAttribute("battle_status", "menu");
    }

    // 天候の取得
    Weather weather = (Weather)session.getAttribute("weather");
    if (weather == null && create_enemy_flag == "create" ) {
      // 敵作成フラグが立っていた場合、ランダムで天候を作成
      Random rnd = new Random();
      int num = rnd.nextInt(3);
      if (num == 0) {
        // 晴れ
        weather = new Weather();
      }else if(num == 1){
        // 雨
        weather = new Weather("雨","ザコ","剣士");
      }else {
        // 曇り
        weather = new Weather("曇り","魔法使い","ザコ");
      }

    }
    session.setAttribute("weather", weather);
    mav.addObject("weather", weather);

    // 敵キャラのセット
    mav.addObject("enemy", wimp);

    // Viewのテンプレート名を設定
    mav.setViewName("Fight");

    return mav;

  }

  /**************************************:
   * 戦闘メソッド
   * @param mav
   * @return
   */
  @RequestMapping(value="/Fight", params="battle=たたかう", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView battle(ModelAndView mav) {

    // メッセージの作成
    List<String> message = new ArrayList<String>();

    // キャラの取得
    Job fencer = (Job)session.getAttribute("character");

    // 敵キャラの取得
    Wimp wimp = (Wimp)session.getAttribute("enemy");

    int damage;
    int new_HP;

    /****************
     * この長い処理はどうにかならないものか。。。
     */
    if (fencer.getSpeed() >= wimp.getSpeed()) {
      // 味方先制時
      damage = CalDamage(fencer.getOffensive_power(),fencer.getLevel(),90,wimp.getDefense_power(),fencer.getName());

      new_HP = wimp.getHit_point() - damage;
      //new_HP = wimp.getHit_point() - fencer.Attack(weather_cor);
      wimp.setHit_point(new_HP);
      message.add("てき　に　 " + damage + "ダメージ　をあたえました");

      // 敵のHP判定
      if(wimp.getHit_point() <= 0) {
        // 経験値の取得
        fencer.addExperience_point(wimp.getExperience_point());
        // メッセージのセットや設定の削除
        mav.addObject("message", battle_win(message,fencer,wimp));
        return show(mav);
      }

      damage = CalDamage(wimp.getOffensive_power(),wimp.getLevel(),70,fencer.getDefense_power(),wimp.getName());
      new_HP = fencer.getHit_point() - damage;
      fencer.setHit_point(new_HP);
      message.add("てき　から " + damage + "ダメージ　うけました");

      // 味方のHP判定
      if(fencer.getHit_point() <= 0) {
        fencer.setHit_point(0);
        session.setAttribute("battle_status", "gameover");
        // メッセージのセット
        message.add("てき　にまけました。。。");
        mav.addObject("message", message);

        return show(mav);
      }

    }else{
      // 敵先制時
      damage = CalDamage(wimp.getOffensive_power(),wimp.getLevel(),70,fencer.getDefense_power(),wimp.getName());
      new_HP = fencer.getHit_point() - damage;
      fencer.setHit_point(new_HP);
      message.add("てき　から " + damage + "ダメージ　うけました");

      // 味方のHP判定
      if(fencer.getHit_point() <= 0) {
        fencer.setHit_point(0);
        session.setAttribute("battle_status", "gameover");
        // メッセージのセット
        message.add("てき　にまけました。。。");
        mav.addObject("message", message);
        return show(mav);
      }

      damage = CalDamage(fencer.getOffensive_power(),fencer.getLevel(),90,wimp.getDefense_power(),fencer.getName());
      new_HP = wimp.getHit_point() - damage;
      wimp.setHit_point(new_HP);
      message.add("てき　に " + damage + "ダメージ　を　あたえました");

      // 敵のHP判定
      if(wimp.getHit_point() <= 0) {
        // 経験値の取得
        fencer.addExperience_point(wimp.getExperience_point());
        // メッセージのセットや設定の削除
        mav.addObject("message", battle_win(message,fencer,wimp));
        return show(mav);
      }

    }

    // メッセージのセット
    mav.addObject("message", message);

    return show(mav);
  }

  /**********************************
   * 敵を倒したときにいろいろと設定を削除したり、セットしたりするメソッド
   */
  public List<String> battle_win(List<String> message,Job job, Wimp wimp) {
    // メッセージの設定
    message.add("てき　を　たおしました");
    message.add("経験値を " + wimp.getExperience_point() + " 取得しました");
    // sessionから敵や設定を削除
    session.removeAttribute("enemy");
    session.removeAttribute("enemy_create_flag");
    session.removeAttribute("weather");
    return message;
  }

  /**********************************
   * クリティカルの抽選メソッド
   */
  public int critical() {
    Random rnd = new Random();
    int num = rnd.nextInt(12);
    return num;
  }
  
  /**********************************
   * ダメージ計算をするメソッド
   * @return
   */
  public int CalDamage(int attack, int level , int power, int defence, String character_name) {

    // 天候取得
    Weather weather = (Weather)session.getAttribute("weather");
    double weather_cor = weather.getWeather_cor(character_name);

    // 天候が雨の場合、味方の防御力も補正をかける
    if(weather.getWeather_name().equals("雨") && character_name.equals("剣士")) {
      defence = (int)(defence * 0.75);
    }

    // クリティカル判定
    if(critical() == 1 && ! character_name.equals("ザコ")) {
      Job fencer = (Job)session.getAttribute("character");
      attack = fencer.Attack(weather_cor) * 2;
    }


    Random rnd = new Random();
    double hosei = 1.0;

    if ( level >= 5 && character_name.equals("ザコ")) {
      double hosei_org = rnd.nextInt(30) + 120;
      hosei = hosei_org / 100.0;
      attack = (int)(attack * hosei);
    }

    int damage = (int) ( ( ( (level * 2 / 5 + 2 ) * ( power * attack / defence ) / 50 + 2 ) ) * weather_cor) ;

    return damage;

  }


  /**********************************
   * 味方のHPをかいふくする
   */
  @RequestMapping(value="/Fight", params="battle=回復する", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView recover(ModelAndView mav) {

    // キャラの取得
    Job fencer = (Job)session.getAttribute("character");
    int recover_hit_point = fencer.getMax_hit_point();
    fencer.setHit_point(recover_hit_point);

    return show(mav);
  }

  /*********************************
   * 敵を出現させる
   */
  @RequestMapping(value="/Fight", params="battle=次にすすむ", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView next_battle(ModelAndView mav) {

    // セッションに敵作成フラグをセット
    session.setAttribute("enemy_create_flag", "create");

    return show(mav);
  }

  /*********************************
   * ステータスを見る
   */
  @RequestMapping(value="/Fight", params="battle=ステータスをみる", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView status(ModelAndView mav) {

    // メッセージの作成
    List<String> message = new ArrayList<String>();

    // キャラの取得
    Job fencer = (Job)session.getAttribute("character");

    message.add("Lv" + fencer.getLevel());
    message.add("ＨＰ　：" + fencer.getHit_point() + " / " + fencer.getMax_hit_point());
    message.add("攻撃力：" + fencer.getOffensive_power());
    message.add("防御力：" + fencer.getDefense_power());
    message.add("素早さ：" + fencer.getSpeed());
    message.add("ランク：" + fencer.getRank());
    message.add("現在の経験値：" + fencer.getExperience_point() + " / " + fencer.getMax_Experience_point());
    int Ex_point = fencer.getMax_Experience_point() - fencer.getExperience_point();
    message.add("次のレベルアップまで：" + Ex_point);

    // メッセージのセット
    mav.addObject("status_list", message);

    return show(mav);
  }


  /*********************************
   * デバックのために敵のステータスを見る
   */
  @RequestMapping(value="/Fight", params="battle=敵のステータスをみる", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView enemy_status(ModelAndView mav) {

    // メッセージの作成
    List<String> message = new ArrayList<String>();

    // 敵キャラの取得
    Wimp enemy = (Wimp)session.getAttribute("enemy");
    message.add("攻撃力：" + enemy.getOffensive_power());
    message.add("防御力：" + enemy.getDefense_power());
    message.add("素早さ：" + enemy.getSpeed());

    // メッセージのセット
    mav.addObject("message", message);

    return show(mav);
  }


}
