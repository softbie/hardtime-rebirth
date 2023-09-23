;//////////////////////////////////////////////////////////////////////////////
;----------------------------- HARD TIME: MISSIONS ----------------------------
;//////////////////////////////////////////////////////////////////////////////

;----------------------------------------------------------------------
;////////////////////////// MISSION PROMOS ////////////////////////////
;----------------------------------------------------------------------
Function MissionPromos(cyc,v,y#)
 ;PHONE INTRO
 If gamPromo=>141 And gamPromo=<170 And GetClient(cyc,v)=0
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, you don't know me but listen up! If you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("do me a favour i'll make it worth your while..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
 EndIf
 ;STRANGER INTRO
 If gamPromo=>141 And gamPromo=<170 And GetClient(cyc,v)=1
  If promoTim>25 And promoTim<325
   Speak(cyc,2) : ChangeRelationship(pChar(cyc),pChar(v),1)
   Outline(translate("Hey, #FIRST#, do you fancy earning some", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("easy money? I've got a little job for you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
 EndIf
 ;GANG INTRO
 If gamPromo=>141 And gamPromo=<170 And GetClient(cyc,v)=2
  If promoTim>25 And promoTim<325
   Speak(cyc,2) : ChangeRelationship(pChar(cyc),pChar(v),1)
   Outline(translate("It's time to prove yourself, #FIRST#!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We need you to do something for the gang..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
 EndIf 
 ;WARDEN INTRO
 If gamPromo=>141 And gamPromo=<170 And GetClient(cyc,v)=3
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Listen, #FIRST#, i've been keeping an eye on your", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("progress and i think changes need to be made..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
 EndIf
 ;1. ACQUIRE STRENGTH
 If gamPromo=141
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charStrength(gamChar(slot))+5
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You're not strong enough to survive in here!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Raise your strength to #FIRST#% by #SECOND#:00 tomorrow...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;2. ACQUIRE AGILITY
 If gamPromo=142
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charAgility(gamChar(slot))+5
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You're not fit enough to keep up with the pace!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Raise your agility to #FIRST#% by #SECOND#:00 tomorrow...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;3. ACQUIRE INTELLIGENCE
 If gamPromo=143
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charIntelligence(gamChar(slot))+5
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("Your lack of knowledge is holding you back!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Raise your intellect to #FIRST#% by #SECOND#:00 tomorrow...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;4. ACQUIRE REPUTATION
 If gamPromo=144
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charReputation(gamChar(slot))+5
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("The people in here don't take you seriously!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Build a reputation of #FIRST#% by #SECOND#:00 tomorrow...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;5. LOSE REPUTATION
 If gamPromo=145
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charReputation(gamChar(slot))-5
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You care more about reputation than rehabilitation!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Reduce it to #FIRST#% by #SECOND#:00 tomorrow...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;6. GET OUT OF DEBT
 If gamPromo=146
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=0
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("Your financial status brings shame on you!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Get out of debt by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;7. ACQUIRE FORTUNE
 If gamPromo=147
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=gamMoney(slot)+250
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You need to get to work and start earning money!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Amass a fortune of $#FIRST# by #SECOND#:00 tomorrow...", GetFigure$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;8. CHANGE HAIRSTYLE
 If gamPromo=148
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charHairStyle(pChar(cyc))
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("That hairstyle is making you a laughing stock!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Grab a comb and copy mine by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;9. CHANGE COSTUME
 If gamPromo=149
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamTarget(slot)=charCostume(pChar(cyc))
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("The way you dress is a crime against fashion!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Grab a mirror and copy mine by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;10. ACQUIRE ITEM
 If gamPromo=150
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   Repeat
    gamTarget(slot)=Rnd(1,weapList)
   Until weapType(pWeapon(cyc))<>gamTarget(slot) And weapType(pWeapon(v))<>gamTarget(slot)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I've been looking everywhere for a #FIRST#!", Lower$(weapName$(gamTarget(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("See if you can get me one by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;11. DELIVER GIVEN ITEM
 If gamPromo=151
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamItem(slot)=weapType(pWeapon(cyc))
   its=0
   Repeat
    gamTarget(slot)=Rnd(1,no_chars)
    its=its+1
   Until (charSnapped(gamTarget(slot)) Or its>100) And charRole(gamTarget(slot))=0 And charLocation(gamTarget(slot))>0 And charLocation(gamTarget(slot))<>gamLocation(slot)
  EndIf
  If promoTim>350 And promoTim<9975 Then ShowPhoto(gamTarget(slot)) 
  If promoTim>350 And promoTim<650
   Speak(cyc,3) : ChangeRelationship(pChar(cyc),gamTarget(slot),1)
   If promoEffect=0 Then ChangeAnim(cyc,25) : ChangeAnim(v,26) : promoEffect=1
   Outline(translate("Find #FIRST# in the #SECOND# and", charName$(gamTarget(slot)), textLocation$(charLocation(gamTarget(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("give him this #FIRST# by #SECOND#:00 tomorrow...", Lower$(weapName$(gamItem(slot))), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;12. FIND & DELIVER ITEM
 If gamPromo=152
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamItem(slot)=Rnd(1,weapList)
   its=0
   Repeat
    gamTarget(slot)=Rnd(1,no_chars)
    its=its+1
   Until (charSnapped(gamTarget(slot)) Or its>100) And charRole(gamTarget(slot))=0 And charLocation(gamTarget(slot))>0 And charLocation(gamTarget(slot))<>gamLocation(slot)
   If cyc>0
    ChangeRelationship(pChar(cyc),gamTarget(slot),1)
   EndIf
  EndIf
  If promoTim>350 And promoTim<9975 Then ShowPhoto(gamTarget(slot)) 
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I need to get a #FIRST# to a guy", Lower$(weapName$(gamItem(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("called '#FIRST#' in the #SECOND#...", charName$(gamTarget(slot)), textLocation$(charLocation(gamTarget(slot)))),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("I'd appreciate it if you could get hold of"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("give him this #FIRST# by #SECOND#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;13. KILL CHARACTER
 If gamPromo=153
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamReward(slot)=500
   gamTarget(slot)=FindVictim(cyc,v)
  EndIf
  If promoTim>350 And promoTim<9975 Then ShowPhoto(gamTarget(slot)) 
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("#FIRST# has got to go! Track him down", charName$(gamTarget(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and kill him for me by #FIRST#:00 tomorrow..", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;14. MUTILATE CHARACTER
 If gamPromo=154
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamReward(slot)=250
   gamTarget(slot)=FindVictim(cyc,v)  
  EndIf
  If promoTim>350 And promoTim<9975 Then ShowPhoto(gamTarget(slot)) 
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("#FIRST# needs to be taught a lesson!", charName$(gamTarget(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Inflict an injury on him by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;15. ASSAULT CHARACTER
 If gamPromo=155
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamTarget(slot)=FindVictim(cyc,v)
  EndIf
  If promoTim>350 And promoTim<9975 Then ShowPhoto(gamTarget(slot)) 
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("#FIRST# has got it coming! Track him down", charName$(gamTarget(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and give him a beating by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;16. MEET CHARACTER
 If gamPromo=156
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   its=0
   Repeat
    gamTarget(slot)=Rnd(1,no_chars)
    its=its+1
   Until (charSnapped(gamTarget(slot))=0 Or its>100) And charRole(gamTarget(slot))=0 And charLocation(gamTarget(slot))>0 And charLocation(gamTarget(slot))<>gamLocation(slot)
   If cyc>0
    ChangeRelationship(pChar(cyc),gamTarget(slot),1)
   EndIf
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I need to reach someone called '#FIRST#'.", charName$(gamTarget(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
   hair$=translate("with unusual hair")
   If charHair(gamTarget(slot))=<2 Then hair$=translate("with dark hair")
   If charHair(gamTarget(slot))=>3 And charHair(gamTarget(slot))=<4 Then hair$=translate("with brown hair")
   If charHair(gamTarget(slot))=5 Then hair$=translate("with red hair")
   If charHair(gamTarget(slot))=>6 And charHair(gamTarget(slot))=<7 Then hair$=translate("with blonde hair")
   If charHair(gamTarget(slot))=>8 And charHair(gamTarget(slot))=<9 Then hair$=translate("with grey hair")
   If charHairStyle(gamTarget(slot))=0 Then hair$=translate("with no hair")
   If charHairStyle(gamTarget(slot))=1 Then hair$=translate("with shaved hair")
   Outline(translate("He's a #FIRST# #SECOND# guy #THIRD#...", Lower$(textModel$(charModel(gamTarget(slot)))), textRace$(GetRace(gamTarget(slot))), hair$),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3) 
   Outline(translate("He should be waiting in the #FIRST#.", textLocation$(charLocation(gamTarget(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Go and meet him there before #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;17. IDENTIFY CHARACTER
 If gamPromo=157
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   its=0
   Repeat
    gamTarget(slot)=Rnd(1,no_chars)
    its=its+1
   Until (charSnapped(gamTarget(slot))=0 Or its>100) And charRole(gamTarget(slot))=0 And charLocation(gamTarget(slot))>0 And charLocation(gamTarget(slot))<>gamLocation(slot)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I need to know who lives in Cell #FIRST# of the #SECOND# Block.", charCell(gamTarget(slot)), textBlock$(charBlock(gamTarget(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Look into it and get back to me by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;18. GUARD CHARACTER
 If gamPromo=158
  If promoTim<350 Then AssignMission(cyc,gamPromo-140)
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I've got a feeling something's about to go down!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Could you watch by back for a little while?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975
   promoTim=9975 : promoUsed(gamPromo)=1
   If gamMins(slot)=>45 Then gamDeadline(slot)=gamHours(slot)+2 Else gamDeadline(slot)=gamHours(slot)+1
  EndIf
 EndIf
 ;19. GET ARRESTED
 If gamPromo=159
  If promoTim<350 Then AssignMission(cyc,gamPromo-140) : gamReward(slot)=500
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("We need to put #FIRST# on the map!", textGang$(charGang(pChar(cyc)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("See if you can get arrested by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;20. JOIN A GANG
 If gamPromo=160
  If promoTim<350
   AssignMission(cyc,gamPromo-140)
   gamReward(slot)=500
   its=0
   Repeat
    conflict=0 : its=its+1
    gamTarget(slot)=Rnd(1,6)
    If cyc>0
     If gamTarget(slot)=charGang(pChar(cyc)) Then conflict=1
    EndIf
    If gamTarget(slot)=charGang(pChar(v)) Then conflict=1 
    If charGangHistory(gamChar(slot),gamTarget(slot))>0 And its<100 Then conflict=1
   Until conflict=0 
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I need to know more about #FIRST#.", textGang$(gamTarget(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Try to infiltrate that gang by #FIRST#:00 tomorrow...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;//////////////////////// MISSION REACTIONS ////////////////////////////
 ;171. MISSION REMINDER
 If gamPromo=171
  If promoTim>25 And promoTim<325 And gamMission(slot)=1
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("raise your strength to #FIRST#% by #SECOND#:00...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>25 And promoTim<325 And gamMission(slot)=2
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("raise your agility to #FIRST#% by #SECOND#:00...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=3
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("raise your intelligence to #FIRST#% by #SECOND#:00...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>25 And promoTim<325 And gamMission(slot)=4
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("acquire a reputation of #FIRST#% by #SECOND#:00...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=5
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("acquire a reputation of #FIRST#% by #SECOND#:00...", gamTarget(slot), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=6
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("get out of debt by #FIRST#:00...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=7
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("amass a fortune of $#FIRST# by #SECOND#:00...", GetFigure$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=8
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("make your hair like mine by #FIRST#:00...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=9
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("make your outfit like mine by #FIRST#:00...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=10
   Speak(cyc,2) 
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("bring me a #FIRST# by #SECOND#:00...", Lower$(weapName$(gamTarget(slot))), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=>11 And gamMission(slot)=<12
   Speak(cyc,2) : ShowPhoto(gamTarget(slot))
   Outline(translate("Hey, remember to deliver that #FIRST# to", Lower$(weapName$(gamItem(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("#FIRST# in the #SECOND# by #THRID#:00...", charName$(gamTarget(slot)), textLocation$(charLocation(gamTarget(slot))), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=13
   Speak(cyc,2) : ShowPhoto(gamTarget(slot))
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("kill #FIRST# by #SECOND#:00...", charName$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=14
   Speak(cyc,2) : ShowPhoto(gamTarget(slot))
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("injure #FIRST# by #SECOND#:00...", charName$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=15
   Speak(cyc,2) : ShowPhoto(gamTarget(slot))
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("attack #FIRST# by #SECOND#:00...", charName$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=16
   Speak(cyc,2)
   Outline(translate("Hey, remember you need to meet a guy called"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("'#FIRST#' in the #SECOND# by #THRID#:00...", charName$(gamTarget(slot)), textLocation$(charLocation(gamTarget(slot))), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And gamMission(slot)=17
   Speak(cyc,2)
   Outline(translate("Hey, remember i need to know who lives in"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Cell #FIRST# of the #SECOND# Block by #THRID#:00...", charCell(gamTarget(slot)), textBlock$(charBlock(gamTarget(slot))), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>25 And promoTim<325 And gamMission(slot)=18
   Speak(cyc,2)
   Outline(translate("Hey, remember i'm depending on you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to watch my back until #FIRST#:00...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>25 And promoTim<325 And gamMission(slot)=19
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to get arrested by #FIRST#:00...", gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>25 And promoTim<325 And gamMission(slot)=20
   Speak(cyc,2)
   Outline(translate("Hey, remember you're on a mission to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("join #FIRST# by #SECOND#:00...", textGang$(gamTarget(slot)), gamDeadline(slot)),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf 
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;172. PHONE FAILURE
 If gamPromo=172 And GetClient(cyc,v)=0
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-5
    charReputation(gamChar(slot))=charReputation(gamChar(slot))-1
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline(translate("You couldn't accomplish one little task for me?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("No wonder you're behind bars, you useless moron!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;172. PEER FAILURE
 If gamPromo=172 And GetClient(cyc,v)=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-5
    charReputation(gamChar(slot))=charReputation(gamChar(slot))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline(translate("You screwed up, #FIRST#! That's the last", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("time i ask you to do something for me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;172. GANG FAILURE
 If gamPromo=172 And GetClient(cyc,v)=2
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-5
    charReputation(gamChar(slot))=charReputation(gamChar(slot))-1
    ChangeGang(gamChar(slot),0)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline(translate("You let down the whole gang, #FIRST#!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're no longer worthy of wearing that ink..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;172. WARDEN FAILURE
 If gamPromo=172 And GetClient(cyc,v)=3
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-5
    charReputation(gamChar(slot))=charReputation(gamChar(slot))+1
    charSentence(gamChar(slot))=charSentence(gamChar(slot))+1 : statTim(6)=-50
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline(translate("You ignored my advice, #FIRST#! Since you're not", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("making progress, i'll just extend your sentence..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;173. PHONE SUCCESS
 If gamPromo=173 And GetClient(cyc,v)=0
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+gamReward(slot)
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
    charReputation(gamChar(slot))=charReputation(gamChar(slot))+1
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline("Nice work, my friend! You did what i asked.",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("I've wired $"+GetFigure$(gamReward(slot))+" to your account as a reward...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;173. PEER SUCCESS
 If gamPromo=173 And GetClient(cyc,v)=1
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    If gamMission(slot)=10
     ChangeAnim(v,25) : ChangeAnim(cyc,26)
    Else
     MakeDeal(cyc,v)
    EndIf
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+gamReward(slot)
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
    charReputation(gamChar(slot))=charReputation(gamChar(slot))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline("Nice work, "+charName$(pChar(v))+"! That really helped me out.",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("You've earned every penny of this $"+GetFigure$(gamReward(slot))+" reward...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;173. GANG SUCCESS
 If gamPromo=173 And GetClient(cyc,v)=2
  If promoTim>25 And promoTim<325 And charGang(pChar(cyc))<>6
   Speak(cyc,3)
   If promoEffect=0
    If gamMission(slot)=10
     ChangeAnim(v,25) : ChangeAnim(cyc,26)
    Else
     MakeDeal(cyc,v)
    EndIf
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+gamReward(slot)
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
    charReputation(gamChar(slot))=charReputation(gamChar(slot))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline("You've done the gang proud, "+charName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("Take $"+GetFigure$(gamReward(slot))+" as a reward for your efforts...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And charGang(pChar(cyc))=6
   Speak(cyc,3)
   If promoEffect=0
    If gamMission(slot)=10 Then ChangeAnim(v,25) : ChangeAnim(cyc,26)
    charSentence(gamChar(slot))=charSentence(gamChar(slot))-1 : statTim(6)=50
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
    charReputation(gamChar(slot))=charReputation(gamChar(slot))-1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline("You're on the right path, "+charName$(pChar(v))+"! You",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("deserve to have a day taken off your sentence...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;173. WARDEN SUCCESS
 If gamPromo=173 And GetClient(cyc,v)=3
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    If gamMission(slot)=10 Then ChangeAnim(v,25) : ChangeAnim(cyc,26)
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
    charReputation(gamChar(slot))=charReputation(gamChar(slot))-1
    charSentence(gamChar(slot))=charSentence(gamChar(slot))-1 : statTim(6)=50
    If charRelation(pChar(cyc),pChar(v))<0 Then ChangeRelationship(pChar(cyc),pChar(v),0)
    gamMission(slot)=0 : gamClient(slot)=0
    promoEffect=1
   EndIf
   Outline("Nice work, "+CellName$(pChar(v))+"! Since you're making progress,",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("i'll have a day taken off your sentence...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;174. MISSED DEADLINE
 If gamPromo=174
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-5
    charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
    promoEffect=1
   EndIf 
   Outline("Damn, i've missed the "+gamDeadline(slot)+":00 deadline!",rX#(400),rY#(520),30,30,30,250,250,250)
   If gamClient(slot)=0 Then Outline("That guy on the phone won't be happy...",rX#(400),rY#(560),30,30,30,250,250,250)
   If gamClient(slot)>0 Then Outline(charName$(gamClient(slot))+" is gonna give me hell...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf 
 ;175. DELIVERED GIVEN ITEM
 If gamPromo=175
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If pWeapon(cyc)>0 And pAnim(cyc)<20 Then ChangeAnim(cyc,25) : ChangeAnim(v,26)
   Outline("Hey, "+charName$(pChar(v))+", "+charName$(gamClient(slot))+" asked me",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("to deliver this "+Lower$(weapName$(gamItem(slot)))+" to you...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(v,3)
   If promoEffect=0
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5
    charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline("Oh, thanks! I gave the money to "+charName$(gamClient(slot))+",",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("so i guess you should ask him for your cut...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;176. FOUND & DELIVERED ITEM
 If gamPromo=176
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If pWeapon(cyc)>0 And pAnim(cyc)<20 Then ChangeAnim(cyc,25) : ChangeAnim(v,26)
   If gamClient(slot)>0
    Outline("Hey, "+charName$(pChar(v))+", "+charName$(gamClient(slot))+" asked me",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline("to deliver this "+Lower$(weapName$(gamItem(slot)))+" to you...",rX#(400),rY#(560),30,30,30,250,250,250)
   Else
    Outline("Hey, "+charName$(pChar(v))+", i received a call asking",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline("me to deliver this "+Lower$(weapName$(gamItem(slot)))+" to you...",rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>350 And promoTim<650
   Speak(v,3)
   If promoEffect=0
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+gamReward(slot)
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+10
    charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    gamMission(slot)=0 : promoEffect=1
   EndIf
   Outline("Oh, thanks! I was going to pay him $"+GetFigure$(gamReward(slot))+",",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("but i guess that money should go to you now...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf 
 ;177. MET CHARACTER
 If gamPromo=177
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If gamClient(slot)>0
    Outline("Hey, "+charName$(pChar(v))+", do you know "+charName$(gamClient(slot))+"?",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline("He asked me to get a message to you...",rX#(400),rY#(560),30,30,30,250,250,250)
   Else
    Outline("Hey, "+charName$(pChar(v))+", i received a call from",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline("someone asking me to meet you here...",rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>350 And promoTim<650
   Speak(v,3)
   If promoEffect=0
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5
    charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline("Yeah, i know what that's about! Thanks for the",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("message. Go back and tell him everything's fine...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf 
 ;178. IDENTIFIED CHARACTER
 If gamPromo=178
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If gamClient(slot)>0
    Outline("Hey, are you from Cell "+charCell(pChar(v))+" of the "+textBlock$(charBlock(pChar(v)))+" Block?",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(charName$(gamClient(slot))+" has been looking for you...",rX#(400),rY#(560),30,30,30,250,250,250)
   Else
    Outline("Hey, are you from Cell "+charCell(pChar(v))+" of the "+textBlock$(charBlock(pChar(v)))+" Block?",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline("Someone on the phone was asking after for you...",rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>350 And promoTim<650
   Speak(v,3)
   If promoEffect=0
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5
    charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline("Yeah, my name is "+charName$(pChar(v))+"! Thanks for the",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("message. Go back and tell him i'm on the way...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;179. WARDEN ASKS YOU TO GIVE UP MISSION
 If gamPromo=179
  ;intro
  optionA$="Yes, abort mission..." : optionB$="No, forget it!" 
  If promoStage=0 And promoTim>25 And promoTim<325 And gamClient(slot)=0
   Speak(cyc,1)
   Outline("Hey, "+CellName$(pChar(v))+", i hear you're running errands",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("for some guy on the other end of a phone?",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>25 And promoTim<325 And gamClient(slot)>0
   Speak(cyc,1)
   Outline("Hey, "+CellName$(pChar(v))+", a little bird tells me that",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("you're running errands for "+charName$(gamClient(slot))+"?",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline("The only orders you have to obey are MINE!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("Give up this nonsense before i get suspicious...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1 
    If charRelation(pChar(cyc),pChar(v))<0 Then ChangeRelationship(pChar(cyc),pChar(v),0)
    CompleteMission(-1)
    promoEffect=1
   EndIf
   Outline("Good for you! He never really cared about you.",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("He was just using you to do his dirty work...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If randy=1 And charGang(pChar(v))>0 And gamWarrant(slot)<2 Then gamWarrant(slot)=2
    promoEffect=1
   EndIf
   Outline("Whatever they're paying you, it's not worth it!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline("I'll make sure you never leave this place...",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
End Function

;-----------------------------------------------------------------
;////////////////////// RELATED FUNCTIONS ////////////////////////
;-----------------------------------------------------------------

;ASSESS CLIENT TYPE
Function GetClient(cyc,v)
 ;phone by default
 value=0
 If cyc>0 And v>0
  ;peer
  If charRole(pChar(cyc))=0 Then value=1
  ;gang member
  If charRole(pChar(cyc))=0 And charGang(pChar(cyc))>0 And charGang(pChar(cyc))=charGang(pChar(v)) Then value=2
  ;warden
  If charRole(pChar(cyc))=1 Then value=3 
 EndIf
 Return value
End Function

;ASSIGN MISSION
Function AssignMission(cyc,mission)
 gamMission(slot)=mission
 If cyc>0
  gamClient(slot)=pChar(cyc)
 Else
  gamClient(slot)=0
 EndIf
 gamDeadline(slot)=gamHours(slot)
 gamReward(slot)=100
 promoUsed(171)=1
End Function

;CHECK MISSIONS
Function CheckMissions()
 If gamMission(slot)>0 And gamPromo<>172 And gamPromo<>173 And charPromo(gamClient(slot),gamChar(slot))<>172 And charPromo(gamClient(slot),gamChar(slot))<>173 And phonePromo<>172 And phonePromo<>173
  ;failed
  result=0
  If gamHours(slot)=gamDeadline(slot) And gamMins(slot)=0 And gamSecs(slot)=0
   If gamMission(slot)=18 Then result=1 Else result=-1
   If result=-1 And gamPromo=0 Then TriggerPromo(gamPlayer(slot),0,174)
   statTim(5)=50
  EndIf
  ;completions
  If (charLocation(gamClient(slot))=gamLocation(slot) And InProximity(gamPlayer(slot),charPlayer(gamClient(slot)),30)) Or gamClient(slot)=<0
   If gamMission(slot)=1 And charStrength(gamChar(slot))=>gamTarget(slot) Then result=1 ;acquired strength 
   If gamMission(slot)=2 And charAgility(gamChar(slot))=>gamTarget(slot) Then result=1 ;acquired agility  
   If gamMission(slot)=3 And charIntelligence(gamChar(slot))=>gamTarget(slot) Then result=1 ;acquired intelligence 
   If gamMission(slot)=4 And charReputation(gamChar(slot))=>gamTarget(slot) Then result=1 ;acquired reputation  
   If gamMission(slot)=5 And charReputation(gamChar(slot))=<gamTarget(slot) Then result=1 ;reduced reputation
   If gamMission(slot)=>6 And gamMission(slot)=<7 And gamMoney(slot)=>gamTarget(slot) Then result=1 ;acquired bank balance
   If gamMission(slot)=8 And charHairStyle(gamChar(slot))=gamTarget(slot) Then result=1 ;changed hairstyle
   If gamMission(slot)=9 And charCostume(gamChar(slot))=gamTarget(slot) Then result=1 ;changed costume
   If gamMission(slot)=10 And weapType(pWeapon(gamPlayer(slot)))=gamTarget(slot) Then result=1 ;acquired item
   If gamMission(slot)=20 And charGang(gamChar(slot))=gamTarget(slot) Then result=1 ;joined gang
  EndIf
  ;deliveries
  If gamMission(slot)=11 Or gamMission(slot)=12
   If charLocation(gamTarget(slot))=gamLocation(slot) And InProximity(gamPlayer(slot),charPlayer(gamTarget(slot)),30) And weapType(pWeapon(gamPlayer(slot)))=gamItem(slot) 
    If gamMission(slot)=11 Then charPromo(gamChar(slot),gamTarget(slot))=175
    If gamMission(slot)=12 Then charPromo(gamChar(slot),gamTarget(slot))=176
    result=1
   EndIf
  EndIf
  ;meetings
  If gamMission(slot)=16 Or gamMission(slot)=17
   If charLocation(gamTarget(slot))=gamLocation(slot) And InProximity(gamPlayer(slot),charPlayer(gamTarget(slot)),30)
    If gamMission(slot)=16 Then charPromo(gamChar(slot),gamTarget(slot))=177
    If gamMission(slot)=17 Then charPromo(gamChar(slot),gamTarget(slot))=178
    result=1
   EndIf
  EndIf
  ;failed to gaurd
  If gamMission(slot)=18
   If charLocation(gamClient(slot))<>gamLocation(slot) Or InProximity(gamPlayer(slot),charPlayer(gamClient(slot)),100)=0 Then result=-1
  EndIf 
  ;trigger reactions
  If result<>0 Then CompleteMission(result) 
 EndIf
End Function

;COMPLETE MISSION
Function CompleteMission(result)
 If gamMission(slot)>0
  If gamClient(slot)>0
   ;client reactions
   If result=-1 Then charPromo(gamClient(slot),gamChar(slot))=172
   If result=1 Then charPromo(gamClient(slot),gamChar(slot))=173
  Else
   ;phone reactions
   If result=-1 Then phonePromo=172
   If result=1 Then phonePromo=173
  EndIf
 EndIf
End Function

;GET PROMO MONEY
Function GetPromoMoney(factor)
 ;randomized figure
 value=Rnd(gamMoney(slot)/5,gamMoney(slot))
 value=value/factor
 ;limits
 If value<100/factor Then value=100/factor
 If value>1000 Then value=1000
 value=RoundOff(value,10)
 If value>gamMoney(slot) Then value=gamMoney(slot)
 Return value
End Function

;GET PHONE PROMO
Function GetPhonePromo()
 ;wrong number by default
 phonePromo=63
 ;social calls
 randy=Rnd(0,5)
 If randy=0 Then phonePromo=64 ;friend calls
 If randy=1 Then phonePromo=65 ;family calls
 ;sentence reduction
 randy=Rnd(0,25)
 If randy=0 And gamMoney(slot)>100 And charSentence(gamChar(slot))=>7 Then phonePromo=66 ;lawyer shaves a day
 If randy=1 And gamMoney(slot)>1000 And charSentence(gamChar(slot))=>12 Then phonePromo=24 ;lawyer shaves a week
 If randy=2 And charGang(gamChar(slot))>0 And charSentence(gamChar(slot))=>12 Then phonePromo=67 ;gang shaves a day
 ;media offers
 randy=Rnd(0,25)
 If randy=0 Then phonePromo=68 ;sell story to journalist
 If randy=1 Then phonePromo=69 ;sell story to filmmaker
 If randy=2 Then phonePromo=261 ;human rights donation
 ;mission assignments
 randy=Rnd(0,50)
 If gamMission(slot)=0
  If randy=1 Then phonePromo=152
  If randy=2 Then phonePromo=153
  If randy=3 Then phonePromo=154
  If randy=4 Then phonePromo=155
  If randy=5 Then phonePromo=156
  If randy=6 Then phonePromo=157
  If randy=7 Then phonePromo=160
 EndIf
 ;bomb threat
 randy=Rnd(0,50)
 If randy=0 Then phonePromo=208
 ;mission reminder
 If gamMission(slot)>0 And gamClient(slot)=0 Then phonePromo=171
 ;avoid recently used
 If phonePromo<>63 And promoUsed(phonePromo)<>0 Then phonePromo=63
End Function

;EXCHANGE MONEY
Function MakeDeal(cyc,v)
 If pAnim(cyc)<20 Then ChangeAnim(cyc,99)
 If pAnim(v)<20 Then ChangeAnim(v,99)
End Function

;FIND VIOLENT MISSION VICTIM
Function FindVictim(cyc,v)
 ;find ideal character
 victim=0 : its=0
 Repeat
  conflict=0 : its=its+1
  victim=Rnd(1,no_chars)
  If cyc>0
   If victim=pChar(cyc) Or charRelation(pChar(cyc),victim)>0 Then conflict=1 
  EndIf
  If victim=pChar(v) Then conflict=1 
  If charLocation(victim)=0 Or charRole(victim)=2 Then conflict=1 
  If charSnapped(victim)=0 And its<100 Then conflict=1
 Until conflict=0
 ;set as enemy
 If cyc>0
  ChangeRelationship(pChar(cyc),victim,-1)
 EndIf
 Return victim
End Function

;RUIN MISSION (when key character leaves)
Function RuinMission(char)
 If gamMission(slot)>0
  ;client gone
  If char=gamClient(slot) Then gamMission(slot)=0
  ;target gone
  If char=gamTarget(slot)
   If gamMission(slot)=>13 And gamMission(slot)=<15 Then CompleteMission(1)
   If gamMission(slot)=11 Or gamMission(slot)=12 Or gamMission(slot)=16 Or gamMission(slot)=17 Then CompleteMission(-1)
  EndIf
 EndIf
End Function