;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: ANIMATIONS ---------------------------
;//////////////////////////////////////////////////////////////////////////////

;-----------------------------------------------------------------
;//////////////// LOAD ANIMATION SEQUENCES ///////////////////////
;-----------------------------------------------------------------
Function LoadSequences(cyc)
 ;source files
 pSeq(cyc,601)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard01.3ds")
 pSeq(cyc,602)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard02.3ds")
 pSeq(cyc,603)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard03.3ds")
 pSeq(cyc,604)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard04.3ds") 
 pSeq(cyc,610)=LoadAnimSeq(p(cyc),"Characters/Sequences/Move_Execute.3ds")
 pSeq(cyc,611)=LoadAnimSeq(p(cyc),"Characters/Sequences/Move_Receive.3ds") 
 ;0-10: stances
 pSeq(cyc,1)=ExtractAnimSeq(p(cyc),0,40,pSeq(cyc,601)) ;standing (bare hands)
 pSeq(cyc,2)=ExtractAnimSeq(p(cyc),1145,1185,pSeq(cyc,601)) ;kneeling (bare hands)
 pSeq(cyc,3)=ExtractAnimSeq(p(cyc),925,965,pSeq(cyc,602)) ;injured stance
 pSeq(cyc,4)=ExtractAnimSeq(p(cyc),1065,1105,pSeq(cyc,602)) ;dazed stance
 pSeq(cyc,5)=ExtractAnimSeq(p(cyc),1980,2020,pSeq(cyc,603)) ;ball stance
 ;10-20: movement
 pSeq(cyc,10)=ExtractAnimSeq(p(cyc),140,220,pSeq(cyc,601)) ;standing turn
 pSeq(cyc,11)=ExtractAnimSeq(p(cyc),1195,1275,pSeq(cyc,601)) ;kneeling turn
 pSeq(cyc,12)=ExtractAnimSeq(p(cyc),915,995,pSeq(cyc,603)) ;walking
 pSeq(cyc,13)=ExtractAnimSeq(p(cyc),50,130,pSeq(cyc,601)) ;running
 pSeq(cyc,14)=ExtractAnimSeq(p(cyc),975,1055,pSeq(cyc,602)) ;injured movement
 pSeq(cyc,15)=ExtractAnimSeq(p(cyc),1115,1195,pSeq(cyc,602)) ;dazed movement
 pSeq(cyc,16)=ExtractAnimSeq(p(cyc),75,155,pSeq(cyc,603)) ;weapon movement
 pSeq(cyc,17)=ExtractAnimSeq(p(cyc),30,110,pSeq(cyc,604)) ;ball walk
 pSeq(cyc,18)=ExtractAnimSeq(p(cyc),120,200,pSeq(cyc,604)) ;ball run 
 ;20-30: weapon interaction
 pSeq(cyc,20)=ExtractAnimSeq(p(cyc),1985,2045,pSeq(cyc,601)) ;pick-up weapon
 pSeq(cyc,21)=ExtractAnimSeq(p(cyc),65,75,pSeq(cyc,602)) ;drop weapon
 pSeq(cyc,22)=ExtractAnimSeq(p(cyc),205,275,pSeq(cyc,602)) ;throw weapon
 pSeq(cyc,23)=ExtractAnimSeq(p(cyc),255,325,pSeq(cyc,603)) ;snatch weapon
 pSeq(cyc,24)=ExtractAnimSeq(p(cyc),165,245,pSeq(cyc,603)) ;examine weapon
 pSeq(cyc,25)=ExtractAnimSeq(p(cyc),1860,1900,pSeq(cyc,603)) ;handover
 pSeq(cyc,26)=ExtractAnimSeq(p(cyc),1910,1970,pSeq(cyc,603)) ;basketball throw
 pSeq(cyc,27)=ExtractAnimSeq(p(cyc),400,440,pSeq(cyc,604)) ;phone pick-up
 ;30-40: hand-2-hand attacks
 pSeq(cyc,30)=ExtractAnimSeq(p(cyc),250,320,pSeq(cyc,601)) ;upper punch
 pSeq(cyc,31)=ExtractAnimSeq(p(cyc),1060,1135,pSeq(cyc,601)) ;lower kick
 pSeq(cyc,32)=ExtractAnimSeq(p(cyc),1405,1455,pSeq(cyc,601)) ;stomp
 pSeq(cyc,33)=ExtractAnimSeq(p(cyc),1675,1745,pSeq(cyc,601)) ;big attack
 pSeq(cyc,34)=ExtractAnimSeq(p(cyc),1775,1875,pSeq(cyc,601)) ;rear attack
 pSeq(cyc,35)=ExtractAnimSeq(p(cyc),105,175,pSeq(cyc,602)) ;rising attack
 ;40-50: weapon swings
 pSeq(cyc,40)=ExtractAnimSeq(p(cyc),305,375,pSeq(cyc,602)) ;upper swing
 pSeq(cyc,41)=ExtractAnimSeq(p(cyc),405,485,pSeq(cyc,602)) ;lower swing
 pSeq(cyc,42)=ExtractAnimSeq(p(cyc),745,815,pSeq(cyc,602)) ;ground swing
 pSeq(cyc,43)=ExtractAnimSeq(p(cyc),515,585,pSeq(cyc,602)) ;big swing
 pSeq(cyc,44)=ExtractAnimSeq(p(cyc),615,715,pSeq(cyc,602)) ;rear swing
 ;50-60: weapon stabs
 pSeq(cyc,51)=ExtractAnimSeq(p(cyc),335,425,pSeq(cyc,603)) ;quick stab
 pSeq(cyc,52)=ExtractAnimSeq(p(cyc),555,625,pSeq(cyc,603)) ;ground stab
 pSeq(cyc,53)=ExtractAnimSeq(p(cyc),455,525,pSeq(cyc,603)) ;big stab
 ;60-70: gun fire
 pSeq(cyc,60)=ExtractAnimSeq(p(cyc),1405,1445,pSeq(cyc,602)) ;rifle stance
 pSeq(cyc,61)=ExtractAnimSeq(p(cyc),1005,1085,pSeq(cyc,603)) ;rifle walk
 pSeq(cyc,62)=ExtractAnimSeq(p(cyc),1455,1535,pSeq(cyc,602)) ;rifle running 
 pSeq(cyc,63)=ExtractAnimSeq(p(cyc),1545,1585,pSeq(cyc,602)) ;rifle fire
 pSeq(cyc,64)=ExtractAnimSeq(p(cyc),1735,1795,pSeq(cyc,602)) ;pistol fire
 pSeq(cyc,65)=ExtractAnimSeq(p(cyc),1645,1725,pSeq(cyc,602)) ;reload
 ;70-80: hurting & blocking
 pSeq(cyc,70)=ExtractAnimSeq(p(cyc),340,390,pSeq(cyc,601)) ;upper hurt
 pSeq(cyc,71)=ExtractAnimSeq(p(cyc),1320,1380,pSeq(cyc,601)) ;lower hurt
 pSeq(cyc,72)=ExtractAnimSeq(p(cyc),1490,1550,pSeq(cyc,601)) ;ground hurt (on back)
 pSeq(cyc,73)=ExtractAnimSeq(p(cyc),1575,1645,pSeq(cyc,601)) ;ground hurt (on front)
 pSeq(cyc,74)=ExtractAnimSeq(p(cyc),1885,1925,pSeq(cyc,601)) ;upper block
 pSeq(cyc,75)=ExtractAnimSeq(p(cyc),1935,1975,pSeq(cyc,601)) ;lower block
 pSeq(cyc,76)=ExtractAnimSeq(p(cyc),825,865,pSeq(cyc,602)) ;upper weapon block
 pSeq(cyc,77)=ExtractAnimSeq(p(cyc),875,915,pSeq(cyc,602)) ;lower weapon block
 pSeq(cyc,78)=ExtractAnimSeq(p(cyc),450,595,pSeq(cyc,604)) ;die (on back)
 pSeq(cyc,79)=ExtractAnimSeq(p(cyc),610,760,pSeq(cyc,604)) ;die (on front)
 ;80-90: falling & rising
 pSeq(cyc,80)=ExtractAnimSeq(p(cyc),415,500,pSeq(cyc,601)) ;fall onto back
 pSeq(cyc,81)=ExtractAnimSeq(p(cyc),510,550,pSeq(cyc,601)) ;lying on back
 pSeq(cyc,82)=ExtractAnimSeq(p(cyc),560,670,pSeq(cyc,601)) ;get up off back
 pSeq(cyc,83)=ExtractAnimSeq(p(cyc),695,780,pSeq(cyc,601)) ;fall onto front (turn)
 pSeq(cyc,84)=ExtractAnimSeq(p(cyc),790,830,pSeq(cyc,601)) ;lying on front
 pSeq(cyc,85)=ExtractAnimSeq(p(cyc),840,910,pSeq(cyc,601)) ;get up off front
 pSeq(cyc,86)=ExtractAnimSeq(p(cyc),935,1030,pSeq(cyc,601)) ;fall onto front (direct)
 pSeq(cyc,87)=ExtractAnimSeq(p(cyc),635,675,pSeq(cyc,603)) ;falling from a height
 pSeq(cyc,88)=ExtractAnimSeq(p(cyc),685,745,pSeq(cyc,603)) ;landing from a fall
 ;90-100: standing gestures
 pSeq(cyc,90)=ExtractAnimSeq(p(cyc),755,835,pSeq(cyc,603)) ;open door
 pSeq(cyc,91)=ExtractAnimSeq(p(cyc),1095,1135,pSeq(cyc,603)) ;friendly wave
 pSeq(cyc,92)=ExtractAnimSeq(p(cyc),260,340,pSeq(cyc,604)) ;sweeping 
 pSeq(cyc,93)=ExtractAnimSeq(p(cyc),950,990,pSeq(cyc,604)) ;smoking
 pSeq(cyc,94)=ExtractAnimSeq(p(cyc),1000,1040,pSeq(cyc,604)) ;injecting
 pSeq(cyc,95)=ExtractAnimSeq(p(cyc),1050,1090,pSeq(cyc,604)) ;drinking
 pSeq(cyc,96)=ExtractAnimSeq(p(cyc),1120,1370,pSeq(cyc,604)) ;breakdown 
 pSeq(cyc,97)=ExtractAnimSeq(p(cyc),1380,1440,pSeq(cyc,604)) ;comb hair 
 pSeq(cyc,98)=ExtractAnimSeq(p(cyc),1450,1510,pSeq(cyc,604)) ;admire reflection
 ;100-120: seated gestures
 pSeq(cyc,100)=ExtractAnimSeq(p(cyc),1145,1155,pSeq(cyc,603)) ;static
 pSeq(cyc,101)=ExtractAnimSeq(p(cyc),1385,1425,pSeq(cyc,603)) ;slouching
 pSeq(cyc,102)=ExtractAnimSeq(p(cyc),1215,1255,pSeq(cyc,603)) ;reading
 pSeq(cyc,103)=ExtractAnimSeq(p(cyc),1265,1375,pSeq(cyc,603)) ;eating
 pSeq(cyc,104)=ExtractAnimSeq(p(cyc),1435,1475,pSeq(cyc,603)) ;building
 pSeq(cyc,105)=ExtractAnimSeq(p(cyc),1485,1515,pSeq(cyc,603)) ;lie down 
 pSeq(cyc,106)=ExtractAnimSeq(p(cyc),1515,1555,pSeq(cyc,603)) ;sleeping
 pSeq(cyc,107)=ExtractAnimSeq(p(cyc),1565,1620,pSeq(cyc,603)) ;get off bed 
 pSeq(cyc,108)=ExtractAnimSeq(p(cyc),1810,1850,pSeq(cyc,603)) ;weight-lifting
 pSeq(cyc,109)=ExtractAnimSeq(p(cyc),210,250,pSeq(cyc,604)) ;typing
 ;120-130: speaking stances
 pSeq(cyc,120)=ExtractAnimSeq(p(cyc),350,390,pSeq(cyc,604)) ;holding phone
 pSeq(cyc,121)=ExtractAnimSeq(p(cyc),755,835,pSeq(cyc,603)) ;hand gestures
 pSeq(cyc,122)=ExtractAnimSeq(p(cyc),770,850,pSeq(cyc,604)) ;hands on hips
 pSeq(cyc,123)=ExtractAnimSeq(p(cyc),860,940,pSeq(cyc,604)) ;folded arms
 ;130-200: additional
 pSeq(cyc,130)=ExtractAnimSeq(p(cyc),1520,1600,pSeq(cyc,604)) ;body changed
 pSeq(cyc,131)=ExtractAnimSeq(p(cyc),1610,1650,pSeq(cyc,604)) ;mourning
 pSeq(cyc,132)=ExtractAnimSeq(p(cyc),1660,1720,pSeq(cyc,604)) ;dumbbell curl
 ;200: moves
 LoadMoveSequences(cyc)
End Function

;----------------------------------------------------------------
;////////////////// MANAGE ANIMATIONS ///////////////////////////
;----------------------------------------------------------------
Function Animations(cyc)
 ;----------- 0-10: STANCES ----------
 ;standing
 If pAnim(cyc)=0
  anim=1 : speeder#=Rnd(0.1,0.3)
  If gamPromo>0 And (cyc=promoActor(1) Or cyc=promoActor(2)) And pSpeaking(cyc)=1
   If pPromoState(cyc)>0 Then anim=120+pPromoState(cyc) : speeder#=Rnd(0.25,0.5)
   If pPromoState(cyc)=>2 And pPromoState(cyc)=<3 Then anim=120+pPromoState(cyc) : speeder#=Rnd(0.1,0.3)
   If pWeapon(cyc)>0 And pPromoState(cyc)=>2 And pPromoState(cyc)=<3 Then anim=121 : speeder#=Rnd(0.25,0.5)
  EndIf
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=4 Then anim=60 : speeder#=Rnd(0.1,0.3)
  If pWeapon(cyc)>0 And weapName$(weapType(pWeapon(cyc)))=translate("Ball") Then anim=5 : speeder#=Rnd(0.1,0.3)
  If pInjured(cyc)>0 Or pHealth(cyc)<10 Then anim=3
  If pDazed(cyc)>0 Then anim=4 : speeder#=Rnd(0.3,0.6)
  If pPhone(cyc)>0 Then anim=120 : speeder#=Rnd(0.1,0.3)
  If (anim=1 Or anim=122 Or anim=123) And (pState(cyc)=1 Or pState(cyc)=122 Or pState(cyc)=123) Then relaxed=1 Else relaxed=0
  If pAnimTim(cyc)=0 Or (anim<>pState(cyc) And relaxed=0)
   Animate p(cyc),1,speeder#,pSeq(cyc,anim),10
   pState(cyc)=anim
  EndIf
  If gotim>50 And pAnimTim(cyc)>30 And pWeapon(cyc)>0 And pPhone(cyc)=0 And cyc<>promoActor(1) And cyc<>promoActor(2)
   If weapName$(weapType(pWeapon(cyc)))=translate("Broom") Then ChangeAnim(cyc,92) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If charRole(pChar(cyc))=0 And weapName$(weapType(pWeapon(cyc)))=translate("Cigarette") Then ChangeAnim(cyc,93) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If charRole(pChar(cyc))=0 And weapName$(weapType(pWeapon(cyc)))=translate("Syringe") Then ChangeAnim(cyc,94) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If charRole(pChar(cyc))=0 And weapName$(weapType(pWeapon(cyc)))=translate("Bottle") Then ChangeAnim(cyc,95) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If weapName$(weapType(pWeapon(cyc)))=translate("Comb") Then ChangeAnim(cyc,97) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If weapName$(weapType(pWeapon(cyc)))=translate("Mirror") Then ChangeAnim(cyc,98) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
   If weapName$(weapType(pWeapon(cyc)))=translate("Dumbbell") Then ChangeAnim(cyc,132) : pAgenda(cyc)=0 : pTA#(cyc)=pA#(cyc)
  EndIf
 EndIf
 ;kneeling
 If pAnim(cyc)=1
  anim=2
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,anim),10
   pState(cyc)=anim
  EndIf
  If pAnimTim(cyc)>5
   If DirPressed(cyc) Or pDazed(cyc)>0 Or cyc=promoActor(1) Or cyc=promoActor(2) Then ChangeAnim(cyc,0)
  EndIf
 EndIf
 ;----------- 10-20: MOVEMENT ----------
 ;standing turn
 If pAnim(cyc)=10
  anim=10
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=4 Then anim=61
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=5 Then anim=17 
  If pInjured(cyc)>0 Or pHealth(cyc)<10 Then anim=14
  If pDazed(cyc)>0 Then anim=15
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   randy=Rnd(-1,1)
   If randy=-1 Then Animate p(cyc),1,-3.0,pSeq(cyc,anim),5
   If randy=>0 Then Animate p(cyc),1,3.0,pSeq(cyc,anim),5
   pState(cyc)=anim
  EndIf
  If pDazed(cyc)>0
   If cLeft(cyc) Then pA#(cyc)=pA#(cyc)-5
   If cRight(cyc) Then pA#(cyc)=pA#(cyc)+5
  Else
   If cLeft(cyc) Then pA#(cyc)=pA#(cyc)+10
   If cRight(cyc) Then pA#(cyc)=pA#(cyc)-10
  EndIf
  pA#(cyc)=CleanAngle#(pA#(cyc))
  If pAnimTim(cyc)>5
   If HorizontalPressed(cyc)=0 Or VerticalPressed(cyc) Then ChangeAnim(cyc,0)
  EndIf
  pStepTim(cyc)=pStepTim(cyc)+1
 EndIf
 ;kneeling turn
 If pAnim(cyc)=11
  anim=11
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   randy=Rnd(-1,1)
   If randy=-1 Then Animate p(cyc),1,-3.0,pSeq(cyc,anim),5
   If randy=>0 Then Animate p(cyc),1,3.0,pSeq(cyc,anim),5
   pState(cyc)=anim
  EndIf
  If cLeft(cyc) Then pA#(cyc)=pA#(cyc)+5
  If cRight(cyc) Then pA#(cyc)=pA#(cyc)-5
  pA#(cyc)=CleanAngle#(pA#(cyc))
  If pAnimTim(cyc)>5
   If HorizontalPressed(cyc)=0 Or VerticalPressed(cyc) Or pDazed(cyc)>0 Then ChangeAnim(cyc,1)
  EndIf
  pStepTim(cyc)=pStepTim(cyc)+1
 EndIf
 ;walking
 If pAnim(cyc)=12
  anim=12 : transition=5
  speeder#=pSpeed#(cyc)*2.0
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=4 Then anim=61
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=5 Then anim=17
  If pHealth(cyc)<10 Then anim=14 : speeder#=pSpeed#(cyc)*3.0
  If pInjured(cyc)>0 Then anim=14 : speeder#=pSpeed#(cyc)*4.0
  If pDazed(cyc)>0 Then anim=15 : speeder#=pSpeed#(cyc)*5.0
  If speeder#<3.0 Then speeder#=3.0
  If pOldAnim(cyc)=1 Or pOldAnim(cyc)=11 Then transition=10
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,speeder#,pSeq(cyc,anim),transition
   pState(cyc)=anim
  EndIf
  ApplyMovement(cyc,pSpeed#(cyc))
  If pAnimTim(cyc)>5 
   If VerticalPressed(cyc)=0 Then ChangeAnim(cyc,0)
  EndIf
  If cDefend(cyc) And pInjured(cyc)=0 And pDazed(cyc)=0 Then ChangeAnim(cyc,13)
  pStepTim(cyc)=pStepTim(cyc)+1
 EndIf
 ;running
 If pAnim(cyc)=13
  anim=13 : transition=5
  speeder#=pSpeed#(cyc)*3.0
  If pWeapon(cyc)>0 Then anim=16
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=4 Then anim=62
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=5 Then anim=18
  If speeder#<3.0 Then speeder#=3.0
  If pOldAnim(cyc)=1 Or pOldAnim(cyc)=11 Then transition=10
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,speeder#,pSeq(cyc,anim),transition
   pState(cyc)=anim
  EndIf
  ApplyMovement(cyc,pSpeed#(cyc)*2)
  If pAnimTim(cyc)>5 
   If VerticalPressed(cyc)=0 Or cDefend(cyc)=0 Or pDazed(cyc)>0 Then ChangeAnim(cyc,0)
  EndIf
  pStepTim(cyc)=pStepTim(cyc)+2
 EndIf
 ;----------- 20-30: WEAPON INTERACTION ----------
 ;pick up weapon
 If pAnim(cyc)=20
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,20),5
  If pAnimTim(cyc)=<11 And WeaponProximity(cyc,v,5)=0
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.3
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sSwing,22050,0.1)
  If pAnimTim(cyc)=11 And HandIntact(cyc,17)
   For v=1 To no_weaps
    range=weapSize#(weapType(v))+5
    If weapLocation(v)=gamLocation(slot) And weapState(v)>0 And pWeapon(cyc)=0 And pWeaponTim(cyc,v)=0 And weapCarrier(v)=0 And pY#(cyc)=>weapY#(v)-15 And pY#(cyc)=<weapY#(v)+15
     If LimbProximity(pLimb(cyc,19),weapX#(v),weapZ#(v),range) Or WeaponProximity(cyc,v,5)
	  ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
	  ProduceSound(p(cyc),weapSound(weapType(v)),22050,0.5)
	  CreateSpurt(weapX#(v),weapY#(v)+1,weapZ#(v),2,10,5) 
	  AttainWeapon(cyc,v) 
	 EndIf
	EndIf
   Next
  EndIf
  If pAnimTim(cyc)>20
   If pWeapon(cyc)>0 And charWeapHistory(pChar(cyc),weapType(pWeapon(cyc)))=0
    ChangeAnim(cyc,24)
   Else
    ChangeAnim(cyc,0)
   EndIf
  EndIf
 EndIf
 ;drop weapon
 If pAnim(cyc)=21
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.0,pSeq(cyc,21),10
  If pAnimTim(cyc)=4 Then DropWeapon(cyc,0)
  If pAnimTim(cyc)>6 Then ChangeAnim(cyc,0)
 EndIf
 ;throw weapon
 If pAnim(cyc)=22
  If pAnimTim(cyc)=0 
   Animate p(cyc),3,3.5,pSeq(cyc,22),5
   weapGravity#(pWeapon(cyc))=1.0
  EndIf
  If pAnimTim(cyc)=6 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<11
   If cThrow(cyc)=1 Or pControl(cyc)=0 Then weapGravity#(pWeapon(cyc))=weapGravity#(pWeapon(cyc))+0.25 
  EndIf
  If pAnimTim(cyc)=11 Then ThrowWeapon(cyc)
  If pAnimTim(cyc)>21 Then ChangeAnim(cyc,0)
 EndIf
 ;snatch weapon
 If pAnim(cyc)=23
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.5,pSeq(cyc,23),5 : pSting(cyc)=1
  If pAnimTim(cyc)=<15 And weapCarrier(NearestWeapon(cyc))>0
   FaceEntity(cyc,weap(NearestWeapon(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.5
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf                                                                    
  If pAnimTim(cyc)=6 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.2,0.5))
  If pAnimTim(cyc)=>9 And pAnimTim(cyc)=<13 And HandIntact(cyc,17) And pSting(cyc)=1
   For v=1 To no_plays
    w=pWeapon(v)
    If w>0 And cyc<>v And pWeapon(cyc)=0 And AttackViable(v)=>1 And AttackViable(v)=<2 And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And pSting(cyc)=1
     If LimbProximity(pLimb(cyc,19),EntityX(pLimb(v,19),1),EntityZ(pLimb(v,19),1),10) Or InRange(cyc,v,4)>0
      charAttacker(pChar(v))=pChar(cyc)
      ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
      ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      CreateSpurt(EntityX(pLimb(v,19),1),EntityY(pLimb(v,19),1)-5,EntityZ(pLimb(v,19),1),2,10,4)
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=0.6
      ChangeAnim(v,71)   
      ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
	  ProduceSound(p(v),weapSound(weapType(w)),22050,0)
	  randy=Rnd(0,2)
      If randy=<1
       HideEntity FindChild(p(v),weapFile$(weapType(w)))
       AttainWeapon(cyc,w)
       pWeapon(v)=0 
      EndIf
      If randy=2 Then DropWeapon(v,0)
      If GetResponse(cyc,v,0)>0 And pChar(cyc)=gamChar(slot) And charPromo(pChar(v),pChar(cyc))=0
       charPromo(pChar(v),pChar(cyc))=17
       If charGang(pChar(v))=6 Then charPromo(pChar(v),pChar(cyc))=44
      EndIf 
      RiskAnger(cyc,v)
      DamageRep(cyc,v,2)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
   For v=1 To no_weaps
    range=weapSize#(weapType(v))+5
    If weapLocation(v)=gamLocation(slot) And weapState(v)>0 And pWeapon(cyc)=0 And pWeaponTim(cyc,v)=0 And weapCarrier(v)=0 And pY#(cyc)=>weapY#(v)-40 And pY#(cyc)<weapY#(v)-10
     If LimbProximity(pLimb(cyc,19),weapX#(v),weapZ#(v),range) Or WeaponProximity(cyc,v,5)
	  ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
	  ProduceSound(p(cyc),weapSound(weapType(v)),22050,0.5)
	  CreateSpurt(weapX#(v),weapY#(v)+1,weapZ#(v),2,10,5) 
	  AttainWeapon(cyc,v) 
	 EndIf
	EndIf
   Next
  EndIf
  If pAnimTim(cyc)>20
   If pWeapon(cyc)>0 And charWeapHistory(pChar(cyc),weapType(pWeapon(cyc)))=0
    ChangeAnim(cyc,24)
   Else
    ChangeAnim(cyc,0)
   EndIf
  EndIf
 EndIf
 ;examine weapon
 If pAnim(cyc)=24
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,Rnd(0.5,1.0),pSeq(cyc,24),15
  If pAnimTim(cyc)>10
   randy=Rnd(0,80)
   If Isolated(cyc,30)=0 Then randy=Rnd(0,40)
   If Isolated(cyc,20)=0 Then randy=Rnd(0,20)
   If Isolated(cyc,10)=0 Then randy=Rnd(0,10)
   If randy=0 Or pAnimTim(cyc)>80 Or charWeapHistory(pChar(cyc),weapType(pWeapon(cyc)))>0 Or pWeapon(cyc)=0
    If pWeapon(cyc)>0 Then charWeapHistory(pChar(cyc),weapType(pWeapon(cyc)))=1
    ChangeAnim(cyc,0)
   EndIf
  EndIf
  pEyes(cyc)=3 : pSpeaking(cyc)=1
 EndIf
 ;handover (execute)
 If pAnim(cyc)=25
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,1.5,pSeq(cyc,25),5
  If pAnimTim(cyc)=13 And pWeapon(cyc)>0
   w=pWeapon(cyc)
   ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
   ProduceSound(p(cyc),weapSound(weapType(w)),22050,0.5)
   DropWeapon(cyc,0)
   If pWeapon(pFoc(cyc))=0 Then AttainWeapon(pFoc(cyc),w)
   TradingRisk(cyc,w)
  EndIf
  If pAnimTim(cyc)>26 Then ChangeAnim(cyc,0)
 EndIf
 ;handover (receive)
 If pAnim(cyc)=26
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,1.5,pSeq(cyc,25),5 : DropWeapon(cyc,0)
  If pAnimTim(cyc)>26
   If pWeapon(cyc)>0 ;And charWeapHistory(pChar(cyc),weapType(pWeapon(cyc)))=0
    TradingRisk(cyc,pWeapon(cyc))
    ChangeAnim(cyc,24)
   Else
    ChangeAnim(cyc,0)
   EndIf
  EndIf
 EndIf
 ;basketball throw
 If pAnim(cyc)=27
  If pAnimTim(cyc)=0 
   Animate p(cyc),3,2.5,pSeq(cyc,26),10
   weapGravity#(pWeapon(cyc))=2.0
  EndIf
  If pAnimTim(cyc)=10 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<16
   If cThrow(cyc)=1 Or pControl(cyc)=0 Then weapGravity#(pWeapon(cyc))=weapGravity#(pWeapon(cyc))+0.2
  EndIf
  If pAnimTim(cyc)=16 Then ThrowWeapon(cyc)
  If pAnimTim(cyc)=20
   ProduceSound(p(cyc),sThud,22050,0.5) : pStepTim(cyc)=99
   pHealth(cyc)=pHealth(cyc)-Rnd(0,1) 
   randy=Rnd(0,100)
   If randy=0 And gamGrowth(slot)=>0 Then gamGrowth(slot)=gamGrowth(slot)-1
  EndIf
  If pAnimTim(cyc)>28 Then ChangeAnim(cyc,0)
 EndIf
 ;phone pick-up
 If pAnim(cyc)=28
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,27),5
  If pAnimTim(cyc)=4 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=8 And pScar(cyc,6)=<4 And pPhone(cyc)=0
   v=PhoneProximity(cyc)
   If v>0 And PhoneTaken(v)=0
    ProduceSound(p(cyc),sPhone,22050,0)
    HideEntity FindChild(world,"Phone"+Dig$(v,10))
    ShowEntity FindChild(p(cyc),"Phone")
    If phoneRing=v
     PositionEntity FindChild(world,"Phone"+Dig$(phoneRing,10)),phoneX#(phoneRing),phoneY#(phoneRing),phoneZ#(phoneRing)
     EntityColor FindChild(world,"Alarm"+Dig$(phoneRing,10)),5,0,0 
     EntityFX FindChild(world,"Alarm"+Dig$(phoneRing,10)),0
     StopChannel chPhone
     phoneRing=0 : phoneTim=0
     If pChar(cyc)=gamChar(slot) And phonePromo>0 Then TriggerPromo(-v,cyc,phonePromo) : phonePromo=0
    EndIf
    pPhone(cyc)=v
   EndIf
  EndIf
  If pAnimTim(cyc)>8 And pPhone(cyc)>0 Then ChangeAnim(cyc,0) : pAgenda(cyc)=0
  If pAnimTim(cyc)>15 Then ChangeAnim(cyc,0)
 EndIf
 ;phone put-down
 If pAnim(cyc)=29
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,27),5
  If pAnimTim(cyc)=4 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=8 And pPhone(cyc)>0
   ProduceSound(p(cyc),sPhone,22050,0)
   HideEntity FindChild(p(cyc),"Phone")
   ShowEntity FindChild(world,"Phone"+Dig$(pPhone(cyc),10))
   pPhone(cyc)=0
  EndIf
  If pAnimTim(cyc)>15 Then ChangeAnim(cyc,0)
 EndIf
 ;----------- 30-40: HAND-2-HAND ATTACKS ----------
 ;upper punch
 If pAnim(cyc)=30
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,4.0,pSeq(cyc,30),5 : pSting(cyc)=1
  If pAnimTim(cyc)=3 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=<15
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.5
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>4 And pAnimTim(cyc)=<10 And pScar(cyc,18)=<4 And pSting(cyc)=1
   For v=1 To no_plays
    range=pAnimTim(cyc)-3
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,15+range) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+5 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<5+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),sImpact(Rnd(1,2)),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+22,pZ#(v),2,10,99)
       ScarLimb(v,1,10)
       ChangeAnim(v,70) : pDT(v)=(110-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
      EndIf
      If blocked=1 
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+22,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       pHP(v)=pHP(v)-Rnd(0,1)
      EndIf
      WeaponImpact(cyc,v,blocked)
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(8-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2 
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,0)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>18 Then ChangeAnim(cyc,0)
  If pWeapon(cyc)>0 And (weapStyle(weapType(pWeapon(cyc)))=1 Or weapStyle(weapType(pWeapon(cyc)))=7) Then ChangeAnim(cyc,40)
 EndIf
 ;lower kick
 If pAnim(cyc)=31
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.5,pSeq(cyc,31),8 : pSting(cyc)=1
  If pAnimTim(cyc)=2 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=<18
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.5
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>4 And pAnimTim(cyc)=<10 And pScar(cyc,32)=<4 And pSting(cyc)=1
   For v=1 To no_plays
    range=pAnimTim(cyc)
    If range>7 Then range=7
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,15+range) And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<5+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+15,pZ#(v),2,10,99)
       ScarArea(v,pX#(v),pY#(cyc)+15,pZ#(v),10)
       ChangeAnim(v,71) : pDT(v)=(110-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)    
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+15,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       pHP(v)=pHP(v)-Rnd(0,1)
      EndIf
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(8-contact)*0.3
      If pStagger#(v)<0.3 Then pStagger#(v)=0.3
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,0)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>22 Then ChangeAnim(cyc,1)
  If pWeapon(cyc)>0 And (weapStyle(weapType(pWeapon(cyc)))=1 Or weapStyle(weapType(pWeapon(cyc)))=7) Then ChangeAnim(cyc,41)
 EndIf
 ;stomp
 If pAnim(cyc)=32
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,32),5 : pSting(cyc)=1
  If pAnimTim(cyc)=3 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=<12
   FaceEntity(cyc,p(pFoc(cyc)),5)
   If InProximity(cyc,pFoc(cyc),10)=0
    RotateEntity pPivot(cyc),0,pA#(cyc),0
    MoveEntity pPivot(cyc),0,0,0.5
   EndIf
  EndIf
  If pAnimTim(cyc)=>7 And pAnimTim(cyc)=<11 And pScar(cyc,35)=<4 And pSting(cyc)=1
   ;For v=1 To no_plays 
   v=pFoc(cyc)
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,25) And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=3 And pSting(cyc)=1
     contact=InRange(cyc,v,6)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
      If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      limb=pLimb(cyc,36)
      CreateSpurt(EntityX(limb,1),pY#(v),EntityZ(limb,1),2,10,99)
      ScarArea(v,EntityX(limb,1),pY#(v),EntityZ(limb,1),10)
      GroundReaction(v) : pDT(v)=pDT(v)-10
      pHealth(v)=pHealth(v)-GetPower(cyc)
      RiskAnger(cyc,v)
      GainStrength(cyc,100)
      DamageRep(cyc,v,0)
      pSting(cyc)=0
     EndIf
    EndIf
   ;Next
  EndIf
  If pAnimTim(cyc)>18 Then ChangeAnim(cyc,0)
  If pWeapon(cyc)>0 And (weapStyle(weapType(pWeapon(cyc)))=<1 Or weapStyle(weapType(pWeapon(cyc)))=7) Then ChangeAnim(cyc,42) 
 EndIf
 ;big attack
 If pAnim(cyc)=33
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,33),10 : pSting(cyc)=1
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.3,0.5))
  If pAnimTim(cyc)=<16
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.5
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>10 And pAnimTim(cyc)=<14 And (pScar(cyc,18)=<4 Or pScar(cyc,5)=<4) And pSting(cyc)=1
   For v=1 To no_plays
    range=pAnimTim(cyc)-8
    If range>5 Then range=5
    If pWeapon(cyc)>0 Then range=range+1
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,15+range) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<3+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),sImpact(3),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,1)
       impactY#=pY#(cyc)+20
       If impactY#>EntityY(pLimb(v,1),1) Then impactY#=EntityY(pLimb(v,1),1) 
       CreateSpurt(pX#(v),impactY#,pZ#(v),2,10,99)
       ScarLimb(v,1,10)
       ChangeAnim(v,70) : pDT(v)=(150-pHealth(v))*2
       pHealth(v)=pHealth(v)-(GetPower(cyc)*2) : pHP(v)=pHP(v)-(GetPower(cyc)*2)
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),sImpact(6),22050,0)
       impactY#=pY#(cyc)+20
       If impactY#>EntityY(pLimb(v,1),1) Then impactY#=EntityY(pLimb(v,1),1) 
       CreateSpurt(pX#(v),impactY#,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
       pHP(v)=pHP(v)-1
      EndIf
      WeaponImpact(cyc,v,blocked)
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(8-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2
      RiskAnger(cyc,v)
      GainStrength(cyc,25)
      DamageRep(cyc,v,1)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>23 Then ChangeAnim(cyc,0)
  If pWeapon(cyc)>0 And (weapStyle(weapType(pWeapon(cyc)))=1 Or weapStyle(weapType(pWeapon(cyc)))=7) Then ChangeAnim(cyc,43)
 EndIf
 ;rear attack
 If pAnim(cyc)=34
  If pAnimTim(cyc)=0
   Animate p(cyc),3,4.0,pSeq(cyc,34),10
   For v=1 To no_plays
    pMultiSting(cyc,v)=1
   Next
  EndIf
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.3,0.5))
  If pAnimTim(cyc)=>5 And pAnimTim(cyc)=<16
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,-1.0
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>7 And pAnimTim(cyc)=<17 And pScar(cyc,18)=<4
   For v=1 To no_plays
    range=pAnimTim(cyc)-8
    If range>5 Then range=5
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And LimbProximity(pLimb(cyc,18),pX#(v),pZ#(v),8) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+5 And AttackViable(v)=1 And pMultiSting(cyc,v)=1
     charAttacker(pChar(v))=pChar(cyc)
     blocked=0
     randy=Rnd(0,10)
     If randy=<3+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
     If blocked=0
      ProduceSound(p(v),sImpact(3),22050,0)
      ProduceSound(p(v),sPain(Rnd(1,8)),22050,1)
      limb=pLimb(cyc,18)
      CreateSpurt(EntityX(limb,1),pY#(cyc)+20,EntityZ(limb,1),2,10,99)
      ScarLimb(v,1,10)
      ChangeAnim(v,70) : pDT(v)=(150-pHealth(v))*2
      pHealth(v)=pHealth(v)-(GetPower(cyc)*2) : pHP(v)=pHP(v)-(GetPower(cyc)*2)
     EndIf
     If blocked=1
      If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
      ProduceSound(p(v),sImpact(6),22050,0)
      CreateSpurt(pX#(v),pY#(cyc)+20,pZ#(v),2,10,4)
      For limb=4 To 29
       If pWeapon(v)=0 Then ScarLimb(v,limb,10)
      Next
      If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
      pHP(v)=pHP(v)-1
     EndIf
     WeaponImpact(cyc,v,blocked)
     pHurtA#(v)=pA#(v)+180 : pStagger#(v)=1.2
     RiskAnger(cyc,v)
     GainStrength(cyc,25)
     DamageRep(cyc,v,1)
     pMultiSting(cyc,v)=0
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>30 Then SharpTransition(cyc,1,180) : ChangeAnim(cyc,0)
  If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=1 Then ChangeAnim(cyc,44) 
 EndIf
 ;rising punch
 If pAnim(cyc)=35
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,35),5 : pSting(cyc)=1
  If pAnimTim(cyc)=3 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=<15
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.2
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>6 And pAnimTim(cyc)=<11 And pScar(cyc,18)=<4 And pSting(cyc)=1
   For v=1 To no_plays
    range=pAnimTim(cyc)-3
    If range>6 Then range=6
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,15+range) And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<5+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+15,pZ#(v),2,10,99)
       ScarArea(v,pX#(v),pY#(cyc)+15,pZ#(v),10)
       ChangeAnim(v,71) : pDT(v)=(110-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+15,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       pHP(v)=pHP(v)-Rnd(0,1)
      EndIf
      WeaponImpact(cyc,v,blocked)
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(8-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,0)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>25 Then ChangeAnim(cyc,1)
  If pWeapon(cyc)>0 And (weapStyle(weapType(pWeapon(cyc)))=1 Or weapStyle(weapType(pWeapon(cyc)))=7) Then ChangeAnim(cyc,41)
 EndIf
 ;----------- 40-50: SWORD ATTACKS ----------
 ;upper swing
 If pAnim(cyc)=40
  anim=40
  If weapStyle(weapType(pWeapon(cyc)))=7 Then anim=51
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,4.0,pSeq(cyc,anim),5 : pSting(cyc)=1
  If pAnimTim(cyc)=3 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<11
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.4
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If weapStyle(weapType(pWeapon(cyc)))=7 Then impactTim=9 Else impactTim=5
  If pAnimTim(cyc)=>impactTim And pAnimTim(cyc)=<impactTim+4 And pSting(cyc)=1
   For v=1 To no_plays
    range=weapRange#(weapType(pWeapon(cyc)))
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,20+range) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<5+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
       If weapStyle(weapType(pWeapon(cyc)))=7 Then ProduceSound(p(v),sStab,22050,1) 
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+20,pZ#(v),2,10,99)
       ScarArea(v,pX#(v),pY#(cyc)+20,pZ#(v),2)
       If CountScars(v)=>2 Then ScarWeapon(pWeapon(cyc),5) : CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(2.0,8.0),1,1) 
       ChangeAnim(v,70) : pDT(v)=(110-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
       pDT(v)=pDT(v)+(weapDamage(weapType(pWeapon(cyc)))*10)
       pHealth(v)=pHealth(v)-Rnd(1,weapDamage(weapType(pWeapon(cyc))))
       pHP(v)=pHP(v)-Rnd(1,weapDamage(weapType(pWeapon(cyc))))  
       If weapName$(weapType(pWeapon(cyc)))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500)
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,0)
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+20,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
       pHP(v)=pHP(v)-Rnd(0,1)
      EndIf
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(range-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,1)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>18 Then ChangeAnim(cyc,0)
 EndIf
 ;lower swing
 If pAnim(cyc)=41
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,4.0,pSeq(cyc,41),5 : pSting(cyc)=1
  If pAnimTim(cyc)=3 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<11
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.4
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>5 And pAnimTim(cyc)=<9 And pSting(cyc)=1
   For v=1 To no_plays
    range=weapRange#(weapType(pWeapon(cyc)))-1
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,20+range) And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<5+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
        If weapStyle(weapType(pWeapon(cyc)))=7 Then ProduceSound(p(v),sStab,22050,1)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+10,pZ#(v),2,10,99)
       ScarArea(v,pX#(v),pY#(cyc)+10,pZ#(v),2)
       If CountScars(v)=>2 Then ScarWeapon(pWeapon(cyc),5) : CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(2.0,8.0),1,1) 
       ChangeAnim(v,71) : pDT(v)=(110-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
       pDT(v)=pDT(v)+(weapDamage(weapType(pWeapon(cyc)))*10)
       pHealth(v)=pHealth(v)-Rnd(1,weapDamage(weapType(pWeapon(cyc))))
       pHP(v)=pHP(v)-Rnd(1,weapDamage(weapType(pWeapon(cyc)))) 
       If weapName$(weapType(pWeapon(cyc)))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500)  
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,0)
       ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+10,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
       pHP(v)=pHP(v)-Rnd(0,1)
      EndIf
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(range-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2
      RiskAnger(cyc,v)
      GainStrength(cyc,50) 
      DamageRep(cyc,v,1)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>20 Then ChangeAnim(cyc,1)
 EndIf
 ;ground swing
 If pAnim(cyc)=42
  anim=42
  If weapStyle(weapType(pWeapon(cyc)))=7 Then anim=52
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.5,pSeq(cyc,anim),10 : pSting(cyc)=1
  If pAnimTim(cyc)=4 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<14
   FaceEntity(cyc,p(pFoc(cyc)),5)
   If InProximity(cyc,pFoc(cyc),15)=0
    RotateEntity pPivot(cyc),0,pA#(cyc),0
    MoveEntity pPivot(cyc),0,0,0.3
   EndIf
  EndIf
  If pAnimTim(cyc)=>10 And pAnimTim(cyc)=<15 And pSting(cyc)=1
   ;For v=1 To no_plays 
   v=pFoc(cyc)
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And pY#(cyc)>pY#(v)-15 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=3 And pSting(cyc)=1
     range=weapRange#(weapType(pWeapon(cyc)))+(weapRange#(weapType(pWeapon(cyc)))/3)
     If LimbProximity(FindChild(p(cyc),weapFile$(weapType(pWeapon(cyc)))),pX#(v),pZ#(v),range) Or LimbProximity(pLimb(cyc,19),pX#(v),pZ#(v),range/2)
      charAttacker(pChar(v))=pChar(cyc)
      ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
      If weapStyle(weapType(pWeapon(cyc)))=7 Then ProduceSound(p(v),sStab,22050,1) 
      If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      limb=FindChild(p(cyc),weapFile$(weapType(pWeapon(cyc))))
      CreateSpurt(pX#(v),pY#(v),pZ#(v),3,10,99)
      ScarArea(v,pX#(v),pY#(v),pZ#(v),2)
      If CountScars(v)=>2 Then ScarWeapon(pWeapon(cyc),5) : CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(2.0,8.0),1,1) 
      GroundReaction(v) : pDT(v)=pDT(v)-10
      pHealth(v)=pHealth(v)-GetPower(cyc)
      pHealth(v)=pHealth(v)-Rnd(1,weapDamage(weapType(pWeapon(cyc))))
      If weapName$(weapType(pWeapon(cyc)))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500)
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,1) 
      pSting(cyc)=0
     EndIf
    EndIf
   ;Next
  EndIf
  If pAnimTim(cyc)>22 Then ChangeAnim(cyc,0)
 EndIf
 ;big swing
 If pAnim(cyc)=43
  anim=43
  If weapStyle(weapType(pWeapon(cyc)))=7 Then anim=53
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,anim),10 : pSting(cyc)=1
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=<16
   FaceEntity(cyc,p(pFoc(cyc)),5)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.4
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>10 And pAnimTim(cyc)=<15 And pSting(cyc)=1
   For v=1 To no_plays
    range=weapRange#(weapType(pWeapon(cyc)))
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,20+range) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+15 And AttackViable(v)=>1 And AttackViable(v)=<2 And pSting(cyc)=1
     contact=InRange(cyc,v,range)
     If contact>0
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<3+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
       If weapStyle(weapType(pWeapon(cyc)))=7 Then ProduceSound(p(v),sStab,22050,1) 
       ProduceSound(p(v),sImpact(3),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,1)
       impactY#=pY#(cyc)+20
       If impactY#>EntityY(pLimb(v,1),1) Then impactY#=EntityY(pLimb(v,1),1)
       CreateSpurt(pX#(v),impactY#,pZ#(v),2,10,99) 
       ScarArea(v,pX#(v),impactY#,pZ#(v),2)
       If CountScars(v)=>2 Then ScarWeapon(pWeapon(cyc),5) : CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(2.0,8.0),1,1) 
       ChangeAnim(v,70) : pDT(v)=(150-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
       pDT(v)=pDT(v)+(weapDamage(weapType(pWeapon(cyc)))*10)
       pHealth(v)=pHealth(v)-weapDamage(weapType(pWeapon(cyc)))
       pHP(v)=pHP(v)-weapDamage(weapType(pWeapon(cyc)))
       If weapName$(weapType(pWeapon(cyc)))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500) 
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,0)
       ProduceSound(p(v),sImpact(6),22050,0)
       impactY#=pY#(cyc)+20
       If impactY#>EntityY(pLimb(v,1),1) Then impactY#=EntityY(pLimb(v,1),1) 
       CreateSpurt(pX#(v),impactY#,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
       pHP(v)=pHP(v)-1
      EndIf
      pHurtA#(v)=pA#(cyc) : pStagger#(v)=(range-contact)*0.2
      If pStagger#(v)<0.2 Then pStagger#(v)=0.2
      RiskAnger(cyc,v)
      GainStrength(cyc,25)
      DamageRep(cyc,v,2)
      pSting(cyc)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>26 Then ChangeAnim(cyc,0)
 EndIf
 ;rear swing
 If pAnim(cyc)=44
  If pAnimTim(cyc)=0
   Animate p(cyc),3,4.0,pSeq(cyc,44),10
   For v=1 To no_plays
    pMultiSting(cyc,v)=1
   Next
  EndIf
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=>5 And pAnimTim(cyc)=<16
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,-1.0
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>7 And pAnimTim(cyc)=<20
   For v=1 To no_plays
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+5 And AttackViable(v)=1 And pMultiSting(cyc,v)=1
     If LimbProximity(FindChild(p(cyc),weapFile$(weapType(pWeapon(cyc)))),pX#(v),pZ#(v),8) Or LimbProximity(pLimb(cyc,18),pX#(v),pZ#(v),8) Or LimbProximity(pLimb(cyc,19),pX#(v),pZ#(v),8)
      charAttacker(pChar(v))=pChar(cyc)
      blocked=0
      randy=Rnd(0,10)
      If randy=<3+BlockPower(v) And pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,p(cyc),90) Then blocked=1
      If blocked=0
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
       If weapStyle(weapType(pWeapon(cyc)))=7 Then ProduceSound(p(v),sStab,22050,1) 
       ProduceSound(p(v),sImpact(3),22050,0)
       ProduceSound(p(v),sPain(Rnd(1,8)),22050,1)
       CreateSpurt(pX#(v),pY#(cyc)+20,pZ#(v),2,10,99)
       ScarArea(v,pX#(v),pY#(cyc)+20,pZ#(v),2)
       If CountScars(v)=>2 Then ScarWeapon(pWeapon(cyc),5) : CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(2.0,8.0),1,1) 
       ChangeAnim(v,70) : pDT(v)=(150-pHealth(v))*2
       pHealth(v)=pHealth(v)-GetPower(cyc) : pHP(v)=pHP(v)-GetPower(cyc)
       pDT(v)=pDT(v)+(weapDamage(weapType(pWeapon(cyc)))*10)
       pHealth(v)=pHealth(v)-weapDamage(weapType(pWeapon(cyc)))
       pHP(v)=pHP(v)-weapDamage(weapType(pWeapon(cyc)))
       If weapName$(weapType(pWeapon(cyc)))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500) 
      EndIf
      If blocked=1
       If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       ProduceSound(p(v),weapSound(weapType(pWeapon(cyc))),22050,1)
       ProduceSound(p(v),sImpact(6),22050,0)
       CreateSpurt(pX#(v),pY#(cyc)+20,pZ#(v),2,10,4)
       For limb=4 To 29
        If pWeapon(v)=0 Then ScarLimb(v,limb,10)
       Next
       If pWeapon(v)=0 Then pHealth(v)=pHealth(v)-1
       pHP(v)=pHP(v)-1
      EndIf
      pHurtA#(v)=pA#(v)+180 : pStagger#(v)=1.2
      RiskAnger(cyc,v)
      GainStrength(cyc,25)
      DamageRep(cyc,v,2)
      pMultiSting(cyc,v)=0
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>30 Then SharpTransition(cyc,1,180) : ChangeAnim(cyc,0)
 EndIf
 ;----------- 60-70: GUN ATTACKS ----------
 ;fire machine gun
 If pAnim(cyc)=60
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,1.5,pSeq(cyc,63),5 : pFireTim(cyc)=0 
  If cDefend(cyc)=0 Or pControl(cyc)=0 Then FaceEntity(cyc,p(pFoc(cyc)),5)
  pFireTim(cyc)=pFireTim(cyc)+1
  If pFireTim(cyc)=5
   ProduceSound(p(cyc),sShot(Rnd(1,2)),22050,0)
   flame=FindChild(p(cyc),"FlameA")
   CreateParticle(EntityX(flame,1),EntityY(flame,1),EntityZ(flame,1),2)
  EndIf
  If pFireTim(cyc)=6 Then FireBullet(cyc)
  If pFireTim(cyc)=>7 Then pFireTim(cyc)=1
  endTim=10
  If pControl(cyc)=0 Then endTim=20
  If pAnimTim(cyc)>endTim And (cAttack(cyc)=0 Or InProximity(cyc,pFoc(cyc),20)) Then ChangeAnim(cyc,0)
  If weapClip(pWeapon(cyc))=0 Then ChangeAnim(cyc,62)
 EndIf
 ;fire pistol
 If pAnim(cyc)=61
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,1.5,pSeq(cyc,64),5 : pFireTim(cyc)=0
  If cDefend(cyc)=0 Or pControl(cyc)=0 Then FaceEntity(cyc,p(pFoc(cyc)),5)
  pFireTim(cyc)=pFireTim(cyc)+1
  If pFireTim(cyc)=5
   ProduceSound(p(cyc),sShot(Rnd(1,2)),22050,1)
   flame=FindChild(p(cyc),"FlameB")
   CreateParticle(EntityX(flame,1),EntityY(flame,1),EntityZ(flame,1),2)
  EndIf
  If pFireTim(cyc)=6 Then FireBullet(cyc)
  If pFireTim(cyc)=>7 Then pFireTim(cyc)=-5
  endTim=10
  If pControl(cyc)=0 Then endTim=20
  If pAnimTim(cyc)>endTim And (cAttack(cyc)=0 Or InProximity(cyc,pFoc(cyc),20)) Then ChangeAnim(cyc,0)
  If weapClip(pWeapon(cyc))=0 Then ChangeAnim(cyc,62)
 EndIf
 ;reload
 If pAnim(cyc)=62
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,3.0,pSeq(cyc,65),5
  If pAnimTim(cyc)=13
   ProduceSound(p(cyc),sReload,22050,0)
   weapClip(pWeapon(cyc))=Rnd(1,10)
   If weapClip(pWeapon(cyc))>weapAmmo(pWeapon(cyc)) Then weapClip(pWeapon(cyc))=weapAmmo(pWeapon(cyc))
  EndIf
  If pAnimTim(cyc)>28 
   ChangeAnim(cyc,0)
   If pControl(cyc)=0 And weapClip(pWeapon(cyc))=0 Then ChangeAnim(cyc,21)
  EndIf
 EndIf
 ;----------- 70-80: HURTING & BLOCKING ----------
 ;upper hurt
 If pAnim(cyc)=70
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,Rnd(1.0,2.0),pSeq(cyc,70),5
  pStagger#(cyc)=pStagger#(cyc)-0.04
  If pAnimTim(cyc)=<10 And pStagger#(cyc)>0
   RotateEntity pPivot(cyc),0,pHurtA#(cyc),0
   MoveEntity pPivot(cyc),0,0,pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>5
   randy=Rnd(0,50)
   If randy=0 And DirPressed(cyc) Then ChangeAnim(cyc,0)
   If randy=<1 And ActionPressed(cyc) Then ChangeAnim(cyc,0)
  EndIf
  If pAnimTim(cyc)>25 Then ChangeAnim(cyc,0)
  DropWeapon(cyc,20)
 EndIf
 ;lower hurt
 If pAnim(cyc)=71
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,Rnd(1.5,2.5),pSeq(cyc,71),5
  pStagger#(cyc)=pStagger#(cyc)-0.04
  If pAnimTim(cyc)=<10 And pStagger#(cyc)>0
   RotateEntity pPivot(cyc),0,pHurtA#(cyc),0
   MoveEntity pPivot(cyc),0,0,pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>5
   randy=Rnd(0,50)
   If randy=0 And DirPressed(cyc) Then ChangeAnim(cyc,0)
   If randy=<1 And ActionPressed(cyc) Then ChangeAnim(cyc,0)
  EndIf
  If pAnimTim(cyc)>25 Then ChangeAnim(cyc,0)
  DropWeapon(cyc,20)
 EndIf
 ;ground hurt (on back)
 If pAnim(cyc)=72
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,Rnd(1.5,2.5),pSeq(cyc,72),5
  randy=Rnd(0,100)
  If randy=0 And pAnimTim(cyc)>5 Then ChangeAnim(cyc,81)
  If pDT(cyc)=<0 And (DirPressed(cyc) Or ActionPressed(cyc) Or pHealth(cyc)=<0) Then ChangeAnim(cyc,81) 
  If pAnimTim(cyc)>24 Then ChangeAnim(cyc,81)
  DropWeapon(cyc,5)
 EndIf
 ;ground hurt (on front)
 If pAnim(cyc)=73
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,Rnd(1.5,2.5),pSeq(cyc,73),5
  randy=Rnd(0,100)
  If randy=0 And pAnimTim(cyc)>5 Then ChangeAnim(cyc,84)
  If pDT(cyc)=<0 And (DirPressed(cyc) Or ActionPressed(cyc) Or pHealth(cyc)=<0) Then ChangeAnim(cyc,84)
  If pAnimTim(cyc)>28 Then ChangeAnim(cyc,84)
  DropWeapon(cyc,5)
 EndIf
 ;upper block
 If pAnim(cyc)=74
  anim=74 : threat=FindThreat(cyc)
  If pWeapon(cyc)>0 Then anim=76
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,Rnd(0.2,0.5),pSeq(cyc,anim),6
   pState(cyc)=anim
  EndIf
  FaceEntity(cyc,p(pFoc(cyc)),5)
  pStagger#(cyc)=pStagger#(cyc)-0.1
  If pStagger#(cyc)>0
   RotateEntity pPivot(cyc),0,pHurtA#(cyc),0
   MoveEntity pPivot(cyc),0,0,pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>5 And pStagger#(cyc)=<0 
   If cDefend(cyc)=0 Or cAttack(cyc)=1 Then ChangeAnim(cyc,0)
  EndIf
  If DirPressed(cyc) Then ChangeAnim(cyc,0)
  If threat=2 Then ChangeAnim(cyc,75)
 EndIf
 ;lower block
 If pAnim(cyc)=75
  anim=75 : threat=FindThreat(cyc)
  If pWeapon(cyc)>0 Then anim=77
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,Rnd(0.2,0.5),pSeq(cyc,anim),6
   pState(cyc)=anim
  EndIf
  FaceEntity(cyc,p(pFoc(cyc)),5)
  pStagger#(cyc)=pStagger#(cyc)-0.1
  If pStagger#(cyc)>0
   RotateEntity pPivot(cyc),0,pHurtA#(cyc),0
   MoveEntity pPivot(cyc),0,0,pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>5 And pStagger#(cyc)=<0 
   If cDefend(cyc)=0 Or cAttack(cyc)=1 Then ChangeAnim(cyc,1)
  EndIf
  If DirPressed(cyc) Then ChangeAnim(cyc,1)
 EndIf
 ;dying
 If pAnim(cyc)=76 Or pAnim(cyc)=77
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,1.0,pSeq(cyc,pAnim(cyc)+2),5
  If pAnimTim(cyc)=5 
   chDeath=EmitSound(sDeath,p(cyc)) 
   If charAttacker(pChar(cyc))>0 And charWitness(charAttacker(pChar(cyc)))>0
    charReputation(charAttacker(pChar(cyc)))=charReputation(charAttacker(pChar(cyc)))+Rnd(1,5)
   EndIf
   If gamMission(slot)=14 And pChar(cyc)=gamTarget(slot) Then CompleteMission(1)
   For v=1 To no_plays
    pAgenda(v)=2 : pFollowFoc(v)=cyc
    pSubX#(v)=9999 : pSubZ#(v)=9999 
   Next
  EndIf
  randy=Rnd(0,200)
  If randy>1 And pControl(cyc)>0 And pAnimTim(cyc)>70 Then randy=Rnd(0,150)
  If randy>1 And pControl(cyc)>0 And pAnimTim(cyc)>100 Then randy=Rnd(0,100)
  If (randy=<1 Or pHealth(cyc)>0) And pAnimTim(cyc)>30 And pAnimTim(cyc)<130
   If ChannelPlaying(chDeath) Then StopChannel chDeath
   If pAnim(cyc)=76 Then ChangeAnim(cyc,82)
   If pAnim(cyc)=77 Then SharpTransition(cyc,85,180) : ChangeAnim(cyc,85)
   pHealth(cyc)=25
   If charAttacker(pChar(cyc))=gamChar(slot) And charWitness(gamChar(slot))>0 And charHealth(charWitness(gamChar(slot)))>0
    If gamWarrant(slot)<12 Then gamWarrant(slot)=12 : gamVictim(slot)=pChar(cyc)
   EndIf
  EndIf
  If pAnimTim(cyc)=150
   If charAttacker(pChar(cyc))>0
    For v=1 To no_chars
     If charRelation(v,pChar(cyc))>0 Then charRelation(v,charAttacker(pChar(cyc)))=-1
     If charAttacker(pChar(cyc))=gamChar(slot) And charPromo(v,gamChar(slot))=0 And v<>gamClient(slot)
      If charRelation(v,pChar(cyc))>0 Then charPromo(v,gamChar(slot))=82 : charPromoRef(v)=pChar(cyc)
      If charRelation(v,pChar(cyc))<0 And charRelation(v,gamChar(slot))=>0 Then charPromo(v,gamChar(slot))=83 : charPromoRef(v)=pChar(cyc)
     EndIf
    Next
    If charWitness(charAttacker(pChar(cyc)))>0
     charReputation(charAttacker(pChar(cyc)))=charReputation(charAttacker(pChar(cyc)))+Rnd(1,5)
     If charAttacker(pChar(cyc))=gamChar(slot) And charHealth(charWitness(gamChar(slot)))>0
      If gamWarrant(slot)=13 Then gamWarrant(slot)=14
      If gamWarrant(slot)<13 Then gamWarrant(slot)=13
      gamVictim(slot)=pChar(cyc)
     EndIf
    EndIf
   EndIf
   If gamMission(slot)>0 And pChar(cyc)=gamClient(slot) Then gamMission(slot)=0
   If gamMission(slot)=>13 And gamMission(slot)=<15 And pChar(cyc)=gamTarget(slot) Then CompleteMission(1)
  EndIf
  If pAnimTim(cyc)>150
   If pChar(cyc)=gamChar(slot) Then fadeTarget#=1.0
   If pChar(cyc)=gamChar(slot) And pAnimTim(cyc)>560 Then go=1
  EndIf
  DropWeapon(cyc,0)
 EndIf
 ;----------- 80-90: FALLING & RISING ----------
 ;fall onto back
 If pAnim(cyc)=80
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.0,pSeq(cyc,80),10 : pStagger#(cyc)=1.75
  pStagger#(cyc)=pStagger#(cyc)-0.04
  If pAnimTim(cyc)=<30 And pStagger#(cyc)>0
   pHurtA#(cyc)=CleanAngle#(pA#(cyc)+180)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,-pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=22
   ProduceSound(p(cyc),sFall,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   ScarArea(cyc,0,0,0,10)
   FindSmashes(cyc) : RiskInjury(cyc,200)
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>45 Then ChangeAnim(cyc,81)
  DropWeapon(cyc,20)
 EndIf
 ;lying on back
 If pAnim(cyc)=81
  If pOldAnim(cyc)=72 Then transition=10 Else transition=5
  If pAnimTim(cyc)=0 Or pHealth(cyc)=<0 Then Animate p(cyc),1,Rnd(0.1,0.4),pSeq(cyc,81),transition
  If pDT(cyc)=<0 And pHealth(cyc)>0
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,82)
  EndIf
  If gotim>0 And pHealth(cyc)=<0 And (gamPromo=0 Or pChar(cyc)<>gamChar(slot)) Then ChangeAnim(cyc,76)
  If gamMission(slot)=15 And pChar(cyc)=gamTarget(slot) And charAttacker(pChar(cyc))=gamChar(slot) Then CompleteMission(1)
  If gamMission(slot)=18 And pChar(cyc)=gamClient(slot) And gamPromo<>158 Then CompleteMission(-1)
 EndIf
 ;get up off back
 If pAnim(cyc)=82
  If pAnimTim(cyc)=0 
   pAnimSpeed#(cyc)=Rnd(2.0,4.0)
   If pInjured(cyc)>0 Then pAnimSpeed#(cyc)=2.0
   Animate p(cyc),3,pAnimSpeed#(cyc),pSeq(cyc,82),5
  EndIf
  If pAnimTim(cyc)=Int(25/pAnimSpeed#(cyc)) Or pAnimTim(cyc)=Int(55/pAnimSpeed#(cyc)) Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),44100,0.5)
  If pAnimTim(cyc)=Int(85/pAnimSpeed#(cyc)) 
   pStepTim(cyc)=99
   If cAttack(cyc)=0 And pControl(cyc)=0 And InProximity(cyc,pFoc(cyc),20) And AttackViable(pFoc(cyc))=>1 And AttackViable(pFoc(cyc))=<2 
    If charAngerTim(pChar(cyc),pChar(pFoc(cyc)))>0 Then cAttack(cyc)=Rnd(0,1)
   EndIf
   If cAttack(cyc)=1 Or cDefend(cyc)=1
    SharpTransition(cyc,2,90)
    If cAttack(cyc)=1 Then ChangeAnim(cyc,35)
    If cDefend(cyc)=1 Then ChangeAnim(cyc,75)
   EndIf
  EndIf
  If pAnimTim(cyc)>Int(115/pAnimSpeed#(cyc)) Then ChangeAnim(cyc,0)
  pHP(cyc)=Rnd(1,charStrength(pChar(cyc))/5)
 EndIf 
 ;fall onto front (turn)
 If pAnim(cyc)=83
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.0,pSeq(cyc,83),10 : pStagger#(cyc)=1.5
  pStagger#(cyc)=pStagger#(cyc)-0.025
  If pAnimTim(cyc)=<35 And pStagger#(cyc)>0
   pHurtA#(cyc)=CleanAngle#(pA#(cyc)+180)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,-pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=22
   ProduceSound(p(cyc),sFall,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   ScarArea(cyc,0,0,0,10)
   FindSmashes(cyc) : RiskInjury(cyc,200)
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>50 Then ChangeAnim(cyc,84)
  DropWeapon(cyc,20)
 EndIf
 ;lying on front
 If pAnim(cyc)=84
  If pOldAnim(cyc)=73 Then transition=10 Else transition=5
  If pAnimTim(cyc)=0 Or pHealth(cyc)=<0 Then Animate p(cyc),1,Rnd(0.1,0.4),pSeq(cyc,84),transition
  If pDT(cyc)=<0 And pHealth(cyc)>0
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1)
    SharpTransition(cyc,85,180) : ChangeAnim(cyc,85)
   EndIf
  EndIf
  If gotim>0 And pHealth(cyc)=<0 And (gamPromo=0 Or pChar(cyc)<>gamChar(slot)) Then ChangeAnim(cyc,77)
  If gamMission(slot)=15 And pChar(cyc)=gamTarget(slot) And charAttacker(pChar(cyc))=gamChar(slot) Then CompleteMission(1) 
  If gamMission(slot)=18 And pChar(cyc)=gamClient(slot) And gamPromo<>158 Then CompleteMission(-1)
 EndIf
 ;get up off front
 If pAnim(cyc)=85
  If pAnimTim(cyc)=0 
   pAnimSpeed#(cyc)=Rnd(2.0,4.0)
   If pInjured(cyc)>0 Then pAnimSpeed#(cyc)=2.0
   Animate p(cyc),3,pAnimSpeed#(cyc),pSeq(cyc,85),5
  EndIf
  If pAnimTim(cyc)=Int(25/pAnimSpeed#(cyc)) Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),44100,0.5)
  If pAnimTim(cyc)=Int(55/pAnimSpeed#(cyc))
   pStepTim(cyc)=99
   If cAttack(cyc)=0 And pControl(cyc)=0 And InProximity(cyc,pFoc(cyc),20) 
    If AttackViable(pFoc(cyc))=>1 And AttackViable(pFoc(cyc))=<2 Then cAttack(cyc)=Rnd(0,1)
   EndIf
   If cAttack(cyc)=1 Then ChangeAnim(cyc,35)
   If cDefend(cyc)=1 Then ChangeAnim(cyc,75)
  EndIf
  If pAnimTim(cyc)>Int(75/pAnimSpeed#(cyc)) Then ChangeAnim(cyc,0)
  pHP(cyc)=Rnd(1,charStrength(pChar(cyc))/5)
 EndIf 
 ;fall onto front (direct)
 If pAnim(cyc)=86
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.5,pSeq(cyc,86),10 : pStagger#(cyc)=1.0
  pStagger#(cyc)=pStagger#(cyc)-0.01
  If pAnimTim(cyc)=<35 And pStagger#(cyc)>0
   pHurtA#(cyc)=pA#(cyc)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,pStagger#(cyc)
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=21
   ProduceSound(p(cyc),sFall,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   ScarArea(cyc,0,0,0,10)
   FindSmashes(cyc) : RiskInjury(cyc,200)
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-Rnd(0,1)
  EndIf
  If pAnimTim(cyc)>45 Then SharpTransition(cyc,84,180) : ChangeAnim(cyc,84) 
  DropWeapon(cyc,20)
 EndIf
 ;falling from a height
 If pAnim(cyc)=87
  If pAnimTim(cyc)=0
   Animate p(cyc),1,Rnd(0.5,1.0),pSeq(cyc,87),5
   If pGravity#(cyc)<0 Then pGravity#(cyc)=0
  EndIf
  If cLeft(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)+5)
  If cRight(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)-5)
  RotateEntity pPivot(cyc),0,pHurtA#(cyc),0
  If pAnimTim(cyc)>100 Then RotateEntity pPivot(cyc),0,pA#(cyc),0
  If gotim>0 Then MoveEntity pPivot(cyc),0,0,1.0
  If pY#(cyc)<pGround#(cyc)+5 
   ProduceSound(p(cyc),sThud,22050,0)
   CreateSpurt(pX#(cyc),pY#(cyc),pZ#(cyc),5,5,4)
   If gotim>0 And pGravity#(cyc)<-3.0 Then pHP(cyc)=pHP(cyc)-1
   If gotim>0 And pGravity#(cyc)=<-10.0 Then pHP(cyc)=0 : pHealth(cyc)=pHealth(cyc)-1
   ChangeAnim(cyc,88)
  EndIf
 EndIf
 ;landing from a fall
 If pAnim(cyc)=88
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,3.0,pSeq(cyc,88),5
  If pAnimTim(cyc)=10 Then pStepTim(cyc)=99
  If (pAnimTim(cyc)>10 And pHP(cyc)=<0) Or pAnimTim(cyc)>20 Then ChangeAnim(cyc,0)
 EndIf 
 ;----------- 90-100: STANDING GESTURES ----------
 ;enter door
 If pAnim(cyc)=90
  If pWeapon(cyc)>0 Then speeder#=-3.0 Else speeder#=3.0 
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,speeder#,pSeq(cyc,90),5
  If SatisfiedAngle(pA#(cyc),doorA#(gamLocation(slot),gamDoor),10)=0
   pA#(cyc)=pA#(cyc)+ReachAngle#(pA#(cyc),doorA#(gamLocation(slot),gamDoor),5)
  EndIf
  If pAnimTim(cyc)=5 Then ProduceSound(cam,sDoor(1),22050,1)
  If pAnimTim(cyc)>10 Then EnterDoor(cyc,gamDoor)
 EndIf
 ;friendly wave
 If pAnim(cyc)=91
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,1.5,pSeq(cyc,91),7
  If pAnimTim(cyc)=10 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+1
  If pAnimTim(cyc)=20 Then Animate p(cyc),3,1.0,pSeq(cyc,1),10
  If pAnimTim(cyc)>25 Then ChangeAnim(cyc,0)
 EndIf
 ;sweeping
 If pAnim(cyc)=92
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,1.0,pSeq(cyc,92),10
  If pAnimTim(cyc)>10
   randy=Rnd(0,50)
   If randy=0 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
   If randy=1 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.0,0.2))
   If randy=<10 Then CreateParticle(EntityX(FindChild(p(cyc),"Broom"),1),pY#(cyc),EntityZ(FindChild(p(cyc),"Broom"),1),5) 
   If pChar(cyc)=gamChar(slot) And LockDown()=0 And pAnimTim(cyc)>160
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+5 : pHealth(cyc)=pHealth(cyc)-1
    ;charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
    randy=Rnd(0,10)
    If randy=0 And charReputation(pChar(cyc))>50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
    If randy=0 And charReputation(pChar(cyc))<50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    If randy=1 Then charStrength(pChar(cyc))=charStrength(pChar(cyc))+1
    pAnimTim(cyc)=10 
   EndIf
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,0)
  EndIf
 EndIf
 ;smoking
 If pAnim(cyc)=93
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.2,0.4),pSeq(cyc,93),10
  If pAnimTim(cyc)>10
   limb=FindChild(p(cyc),"Cigar")
   randy=Rnd(0,5)
   If randy=0 Then CreateParticle(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),2) 
   If randy=1 Then CreateParticle(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),7) 
   randy=Rnd(0,100)
   If randy=0 Then ProduceSound(p(cyc),sChoke,22050,Rnd(0.1,0.5))
   If randy=<2 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+1 : pHealth(cyc)=pHealth(cyc)-1
   randy=Rnd(0,1000)
   If randy=<1 And gamLocation(slot)<>11 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
   If randy=2 Then charStrength(pChar(cyc))=charStrength(pChar(cyc))-1
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,0)
   ExhaustDrug(cyc)
  EndIf
 EndIf
 ;injecting
 If pAnim(cyc)=94
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,94),10
  If pAnimTim(cyc)>10 
   randy=Rnd(0,100)
   If randy=0 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   If randy=<2 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1 : pHealth(cyc)=pHealth(cyc)+1
   If pInjured(cyc)>0 Then pInjured(cyc)=pInjured(cyc)-Rnd(0,1)
   randy=Rnd(0,1000)
   If randy=<1 And gamLocation(slot)<>11 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
   If randy=2 Then charAgility(pChar(cyc))=charAgility(pChar(cyc))-1
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,0)
   ExhaustDrug(cyc)
  EndIf
 EndIf 
 ;drinking
 If pAnim(cyc)=95
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.25,0.5),pSeq(cyc,95),10
  If pAnimTim(cyc)>10 
   limb=FindChild(p(cyc),"Bottle")
   randy=Rnd(0,5)
   If randy=0 Then CreateParticle(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),14)
   randy=Rnd(0,100)
   If randy=0 Then ProduceSound(p(cyc),sDrink,22050,0)
   If randy=1 Then ProduceSound(p(cyc),sBottle,22050,Rnd(0.1,0.3)) 
   If randy=<2 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+1 : pHealth(cyc)=pHealth(cyc)-1 
   randy=Rnd(0,1000)
   If randy=<1 And gamLocation(slot)<>11 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
   If randy=2 Then charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))-1
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,0)
   ExhaustDrug(cyc)
  EndIf
 EndIf 
 ;nervous breakdown
 If pAnim(cyc)=96
  If pAnimTim(cyc)=0 
   Animate p(cyc),3,1.0,pSeq(cyc,96),20
   ProduceSound(p(cyc),sBreakdown,22050,1)
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   If gamMission(slot)=14 And pChar(cyc)=gamTarget(slot) Then CompleteMission(1)
  EndIf
  If pAnimTim(cyc)=35 Or pAnimTim(cyc)=150 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=60 Or pAnimTim(cyc)=180 Or pAnimTim(cyc)=240 Or pAnimTim(cyc)=270 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>270
   charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))-1
   If charHappiness(pChar(cyc))<50 Then charHappiness(pChar(cyc))=50
   charBreakdown(pChar(cyc))=1000
   ChangeAnim(cyc,0) : pAgenda(cyc)=2
  EndIf
  DropWeapon(cyc,20)
 EndIf
 ;comb hair
 If pAnim(cyc)=97
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.25,0.5),pSeq(cyc,97),10
  If pAnimTim(cyc)>10 
   If pControl(cyc)>0
    oldStyle=charHairStyle(pChar(cyc)) : oldHair=charHair(pChar(cyc))
    If cLeft(cyc)=1 And keytim=0 Then charHairStyle(pChar(cyc))=charHairStyle(pChar(cyc))-1 : PlaySound sMenuBrowse : keytim=6
    If cRight(cyc)=1 And keytim=0 Then charHairStyle(pChar(cyc))=charHairStyle(pChar(cyc))+1 : PlaySound sMenuBrowse : keytim=6
    If cUp(cyc)=1 And keytim=0 Then charHair(pChar(cyc))=charHair(pChar(cyc))-1 : PlaySound sMenuBrowse : keytim=6
    If cDown(cyc)=1 And keytim=0 Then charHair(pChar(cyc))=charHair(pChar(cyc))+1 : PlaySound sMenuBrowse : keytim=6
    If charHairStyle(pChar(cyc))<0 Then charHairStyle(pChar(cyc))=no_hairstyles
    If charHairStyle(pChar(cyc))>no_hairstyles Then charHairStyle(pChar(cyc))=0
    If charHair(pChar(cyc))<1 Then charHair(pChar(cyc))=no_hairs
    If charHair(pChar(cyc))>no_hairs Then charHair(pChar(cyc))=1
    If charHairStyle(pChar(cyc))<>oldStyle Or charHair(pChar(cyc))<>oldHair
     ApplyCostume(cyc)
    EndIf
   EndIf
   If (DirPressed(cyc) And pControl(cyc)=0) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1)
    ChangeAnim(cyc,0)
   EndIf
  EndIf
 EndIf 
 ;admire reflection
 If pAnim(cyc)=98
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.25,0.5),pSeq(cyc,98),10
  If pAnimTim(cyc)>10 
   If pControl(cyc)>0
    oldCostume=charCostume(pChar(cyc)) : oldSpecs=charSpecs(pChar(cyc))
    If cLeft(cyc)=1 And keytim=0 Then charCostume(pChar(cyc))=charCostume(pChar(cyc))-1 : PlaySound sMenuBrowse : keytim=6
    If cRight(cyc)=1 And keytim=0 Then charCostume(pChar(cyc))=charCostume(pChar(cyc))+1 : PlaySound sMenuBrowse : keytim=6
    If cUp(cyc)=1 And keytim=0 Then charSpecs(pChar(cyc))=charSpecs(pChar(cyc))-1 : PlaySound sMenuBrowse : keytim=6
    If cDown(cyc)=1 And keytim=0 Then charSpecs(pChar(cyc))=charSpecs(pChar(cyc))+1 : PlaySound sMenuBrowse : keytim=6
    If charCostume(pChar(cyc))<0 Then charCostume(pChar(cyc))=no_costumes
    If charCostume(pChar(cyc))>no_costumes Then charCostume(pChar(cyc))=0
    If charSpecs(pChar(cyc))<0 Then charSpecs(pChar(cyc))=no_specs
    If charSpecs(pChar(cyc))>no_specs Then charSpecs(pChar(cyc))=0
    If charCostume(pChar(cyc))<>oldCostume Or charSpecs(pChar(cyc))<>oldSpecs
     ApplyCostume(cyc)
    EndIf
   EndIf
   If (DirPressed(cyc) And pControl(cyc)=0) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1)
    ChangeAnim(cyc,0)
   EndIf
  EndIf
 EndIf 
 ;mock handover (money)
 If pAnim(cyc)=99
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,1.5,pSeq(cyc,25),5
  If pAnimTim(cyc)>26 Then ChangeAnim(cyc,0)
 EndIf
 ;----------- 100-110: SITTING GESTURES ----------
 ;sit down
 If pAnim(cyc)=100 And pSeat(cyc)>0
  EntityType pPivot(cyc),0,0
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,2.0,pSeq(cyc,10),5
  If pAnimTim(cyc)=5 Then Animate p(cyc),1,1.0,pSeq(cyc,100),10
  If pAnimTim(cyc)=5 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=10 Then ProduceSound(p(cyc),sThud,22050,0.3) : ProduceSound(p(cyc),sGeneric,22050,0.3)
  If pAnimTim(cyc)>10 Then ChangeAnim(cyc,102) : pAgenda(cyc)=0
  If weapType(pWeapon(cyc))<>16 Then DropWeapon(cyc,0)
 EndIf
 ;lie down
 If pAnim(cyc)=100 And pBed(cyc)>0
  EntityType pPivot(cyc),0,0
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.0,pSeq(cyc,105),1
  If pAnimTim(cyc)=5 Then Animate p(cyc),1,1.0,pSeq(cyc,106),15
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)>15 Then ChangeAnim(cyc,103) : pAgenda(cyc)=0
  DropWeapon(cyc,0)
 EndIf
 ;get off chair
 If pAnim(cyc)=101 And pSeat(cyc)>0
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,2.0,pSeq(cyc,10),5
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=5 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>10
   ResetEntity pPivot(cyc)
   PositionEntity pPivot(cyc),pX#(cyc),pY#(cyc)+18,pZ#(cyc)
   EntityType pPivot(cyc),1,0
   EntityRadius pPivot(cyc),8,18
   ChangeAnim(cyc,0) : pSeat(cyc)=0
  EndIf
 EndIf
 ;get off bed
 If pAnim(cyc)=101 And pBed(cyc)>0
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,2.0,pSeq(cyc,107),1
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=15 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>20
   ResetEntity pPivot(cyc)
   PositionEntity pPivot(cyc),pX#(cyc),pY#(cyc)+18,pZ#(cyc)
   EntityType pPivot(cyc),1,0
   EntityRadius pPivot(cyc),8,18
   ChangeAnim(cyc,0) : pBed(cyc)=0
  EndIf
 EndIf
 ;sitting loop
 If pAnim(cyc)=102
  anim=101 : speeder#=Rnd(0.1,0.3)
  If gamLocation(slot)=2 And pSeat(cyc)=>1 And pSeat(cyc)=<3 Then anim=108 : speeder#=Rnd(0.5,1.0)
  If OnComputer(cyc)
   If pAnimTim(cyc)=0 Then gamFile=Rnd(1,no_chars)
   If charIntelligence(pChar(cyc))=>70 Then anim=109 : speeder#=Rnd(0.25,0.5)
   If charIntelligence(pChar(cyc))<70 And pChar(cyc)=gamChar(slot) And gamPromo=0 And promoUsed(19)=0 Then TriggerPromo(cyc,0,19)
  EndIf
  If charRole(pChar(cyc))=0
   If gamLocation(slot)=4 And pSeat(cyc)=>1 And pSeat(cyc)=<3 Then anim=102 : speeder#=Rnd(0.1,0.3)
   If cyc<>promoActor(1) And cyc<>promoActor(2)
    If gamLocation(slot)=4 And pSeat(cyc)=4
     If charIntelligence(pChar(cyc))=>70 Then anim=104 : speeder#=0.5
     If charIntelligence(pChar(cyc))<70 And pChar(cyc)=gamChar(slot) And gamPromo=0 And promoUsed(21)=0 Then TriggerPromo(cyc,0,21)
    EndIf
    If gamLocation(slot)=6 And pSeat(cyc)=9
     If charIntelligence(pChar(cyc))=>80 Then anim=104 : speeder#=0.5
     If charIntelligence(pChar(cyc))<80 And pChar(cyc)=gamChar(slot) And gamPromo=0 And promoUsed(22)=0 Then TriggerPromo(cyc,0,22)
    EndIf
    If gamLocation(slot)=8 And FindChild(world,"Tray"+Dig$(pSeat(cyc),10))>0 And gamPromo<>27
     If trayState(pSeat(cyc))>0 Or pFoodTim(cyc)>0 Then anim=103 : speeder#=1.0
    EndIf
    If gamLocation(slot)=8 And pSeat(cyc)=45
     If charIntelligence(pChar(cyc))=>60 Then anim=104 : speeder#=0.5
     If charIntelligence(pChar(cyc))<60 And pChar(cyc)=gamChar(slot) And gamPromo=0 And promoUsed(20)=0 Then TriggerPromo(cyc,0,20)
    EndIf
    If gamLocation(slot)=10 And pSeat(cyc)=<6 And charRole(pChar(cyc))=0
     If kitState(pSeat(cyc))>0 And charStrength(pChar(cyc))=>70 Then anim=104 : speeder#=0.5
     If kitState(pSeat(cyc))>0 And charStrength(pChar(cyc))<70 And pChar(cyc)=gamChar(slot) And gamPromo=0 And promoUsed(23)=0 Then TriggerPromo(cyc,0,23)
    EndIf
   EndIf
  EndIf
  If pAnimTim(cyc)=0 Or anim<>pState(cyc)
   Animate p(cyc),1,speeder#,pSeq(cyc,anim),10
   pState(cyc)=anim : pFoodTim(cyc)=0
  EndIf
  SittingEffects(cyc)
  If pAnimTim(cyc)>10 And keytim=0
   randy=Rnd(0,500)
   If (randy=0 Or pAgenda(cyc)=2 Or pAgenda(cyc)=4) And pControl(cyc)=0 Then ChangeAnim(cyc,101)
   If pControl(cyc)>0 And (DirPressed(cyc) Or ActionPressed(cyc) Or KeyDown(1)) Then ChangeAnim(cyc,101)
   If pAnim(cyc)=101 And pState(cyc)=108 Then ProduceSound(p(cyc),sThud,22050,0) : ProduceSound(p(cyc),sAxe,22050,0)
  EndIf
 EndIf
 ;sleeping
 If pAnim(cyc)=103
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.1,0.4),pSeq(cyc,106),5
  randy=Rnd(0,100)
  If pInjured(cyc)>0 Then randy=Rnd(0,300)
  If randy=0 Then ProduceSound(p(cyc),sSnore,8000,Rnd(0.1,1.0))
  If randy=<1 And pHealth(cyc)<100 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+1
  If randy=<3 And pHealth(cyc)=>100 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1 
  If randy=<3 Then pHealth(cyc)=pHealth(cyc)+1
  If pAnimTim(cyc)>10
   randy=Rnd(0,500)
   If (randy=0 Or pAgenda(cyc)=2) And pControl(cyc)=0 And LockDown()=0 Then ChangeAnim(cyc,101)
   If pControl(cyc)>0 And (DirPressed(cyc) Or ActionPressed(cyc) Or KeyDown(1)) Then ChangeAnim(cyc,101)
   If (cyc=promoActor(1) Or cyc=promoActor(2)) And gamPromo<>8 And gamPromo<>11 Then ChangeAnim(cyc,101)
  EndIf
 EndIf
 ;------------- 130+: ADDITIONAL -------------
 ;changed body shape
 If pAnim(cyc)=130
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,0.5,pSeq(cyc,130),5
  If pAnimTim(cyc)>150 Or (pAnimTim(cyc)>80 And gamPromo=0) Then ChangeAnim(cyc,0)
 EndIf
 ;mourning
 If pAnim(cyc)=131
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.25,0.5),pSeq(cyc,131),10 
  If gamPromo<>31
   abort=0
   randy=Rnd(0,1000)
   If randy=0 And pControl(cyc)=0 Then abort=1
   If pControl(cyc)>0 And (DirPressed(cyc) Or ActionPressed(cyc)) Then abort=1
   If abort=1 And pAnimTim(cyc)>50 Then ChangeAnim(cyc,0)
   If pAnimTim(cyc)>600 Or cyc=promoActor(1) Or cyc=promoActor(2) Then ChangeAnim(cyc,0)
  EndIf
 EndIf
 ;dumbbell curl
 If pAnim(cyc)=132
  If pAnimTim(cyc)=0 Then Animate p(cyc),1,Rnd(0.3,0.6),pSeq(cyc,132),10
  If pAnimTim(cyc)>10 
   randy=Rnd(0,300)
   If randy=<2 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,Rnd(0.1,0.5))
   If randy=0 
    charStrength(pChar(cyc))=charStrength(pChar(cyc))+1
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
    randy=Rnd(0,5)
    If randy=0 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
    pHealth(cyc)=pHealth(cyc)-1
    randy=Rnd(0,50)
    If randy=0 And gamGrowth(slot)=<0 Then gamGrowth(slot)=gamGrowth(slot)+1
   EndIf
   If DirPressed(cyc) Or ActionPressed(cyc) Or cyc=promoActor(1) Or cyc=promoActor(2) Or KeyDown(1) Then ChangeAnim(cyc,0)
  EndIf
 EndIf
 ;------------- MOVES -------------
 MoveAnims(cyc)
 ;INCREMENTATION
 pAnimTim(cyc)=pAnimTim(cyc)+1
End Function

;--------------------------------------------------------------
;////////////////// RELATED FUNCTIONS /////////////////////////
;--------------------------------------------------------------

;CHANGE ANIMATION
Function ChangeAnim(cyc,anim)
 pOldAnim(cyc)=pAnim(cyc)
 pAnim(cyc)=anim
 If pOldAnim(cyc)>anim Then pAnimTim(cyc)=-1 Else pAnimTim(cyc)=0
End Function

;IMMEDIATE TRANSITION
Function SharpTransition(cyc,anim,offset#)
 ;honour current co-ords
 pX#(cyc)=EntityX(pLimb(cyc,30),1)
 pZ#(cyc)=EntityZ(pLimb(cyc,30),1)
 pOldX#(cyc)=pX#(cyc) : pOldZ#(cyc)=pZ#(cyc)
 ;orientation
 If offset#<0 Then pA#(cyc)=EntityYaw(pLimb(cyc,30),1)
 If offset#=>0 Then pA#(cyc)=pA#(cyc)+offset# 
 pA#(cyc)=CleanAngle#(pA#(cyc)) 
 pTA#(cyc)=pA#(cyc)
 ;immediate transition 
 PositionEntity pPivot(cyc),pX#(cyc),EntityY(pPivot(cyc)),pZ#(cyc)
 RotateEntity pPivot(cyc),0,pA#(cyc),0
 Animate p(cyc),1,1,pSeq(cyc,anim),0
End Function

;POINT BODY
Function PointBody(cyc,entity)
 ;identify limbs involved
 limb=FindChild(p(cyc),"Body")
 source=FindChild(p(cyc),"Hips")
 ;stabilize and point
 RotateEntity limb,EntityPitch(source),EntityYaw(source),EntityRoll(source) 
 PointEntity limb,entity
 If pAnim(cyc)=60 ;machine gun
  RotateEntity limb,EntityPitch(limb)+5,EntityYaw(limb)-40,EntityRoll(limb)
  If AttackViable(pFoc(cyc))=3 Then RotateEntity limb,EntityPitch(limb)+10,EntityYaw(limb),EntityRoll(limb)
 EndIf
 If pAnim(cyc)=61 ;pistol
  RotateEntity limb,EntityPitch(limb)+10,EntityYaw(limb)+36,EntityRoll(limb)
  If AttackViable(pFoc(cyc))=3 Then RotateEntity limb,EntityPitch(limb)+15,EntityYaw(limb),EntityRoll(limb)
 EndIf 
 ;X limitations
 If EntityPitch(limb)<EntityPitch(source)-50 Then RotateEntity limb,EntityPitch(source)-50,EntityYaw(limb),EntityRoll(limb)
 If EntityPitch(limb)>EntityPitch(source)+60 Then RotateEntity limb,EntityPitch(source)+60,EntityYaw(limb),EntityRoll(limb)
 ;Y limitations
 If EntityYaw(limb)<EntityYaw(source)-30 Then RotateEntity limb,EntityPitch(limb),EntityYaw(source)-30,EntityRoll(limb)
 If EntityYaw(limb)>EntityYaw(source)+30 Then RotateEntity limb,EntityPitch(limb),EntityYaw(source)+30,EntityRoll(limb) 
 ;Z limitations
 RotateEntity limb,EntityPitch(limb),EntityYaw(limb),0
End Function

;POINT HEAD
Function PointHead(cyc,entity)
 ;identify limbs involved
 limb=FindChild(p(cyc),"Head")
 source=FindChild(p(cyc),"Body")
 ;stabilize and point
 RotateEntity limb,EntityPitch(source),EntityYaw(source),EntityRoll(source) 
 PointEntity limb,entity
 ;X limitations
 If EntityPitch(limb)<EntityPitch(source)-60 Then RotateEntity limb,EntityPitch(source)-60,EntityYaw(limb),EntityRoll(limb)
 If EntityPitch(limb)>EntityPitch(source)+10 Then RotateEntity limb,EntityPitch(source)+10,EntityYaw(limb),EntityRoll(limb)
 ;Y limitations
 If EntityYaw(limb)<EntityYaw(source)-45 Then RotateEntity limb,EntityPitch(limb),EntityYaw(source)-45,EntityRoll(limb)
 If EntityYaw(limb)>EntityYaw(source)+45 Then RotateEntity limb,EntityPitch(limb),EntityYaw(source)+45,EntityRoll(limb) 
 ;Z limitations
 RotateEntity limb,EntityPitch(limb),EntityYaw(limb),0
 ;preserve long hair
 If FindChild(p(cyc),"Hair_Long")>0
  RotateEntity FindChild(p(cyc),"Hair_Long"),-EntityPitch(limb),-(EntityYaw(limb)-(EntityYaw(limb)/3)),-(EntityRoll(limb)/2)
  If EntityPitch(FindChild(p(cyc),"Hair_Long"))<EntityPitch(source)-10
   RotateEntity FindChild(p(cyc),"Hair_Long"),EntityPitch(source)-10,EntityYaw(FindChild(p(cyc),"Hair_Long")),EntityRoll(FindChild(p(cyc),"Hair_Long"))
  EndIf
 EndIf
End Function

;TURN TO FACE ENTITY
Function FaceEntity(cyc,entity,turner#)
 If entity>0
  PositionEntity dummy,pX#(cyc),pY#(cyc),pZ#(cyc)
  PointEntity dummy,entity
  tA#=CleanAngle#(EntityYaw(dummy))
  If SatisfiedAngle(pA#(cyc),tA#,turner#*2)=0
   pA#(cyc)=pA#(cyc)+ReachAngle#(pA#(cyc),tA#,turner#)
  EndIf
 EndIf
End Function

;APPLY MOVEMENT
Function ApplyMovement(cyc,speed#)
 ;turn
 If pDazed(cyc)>0
  If cLeft(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)-5)
  If cRight(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)+5)
 Else
  If cLeft(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)+5)
  If cRight(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)-5)
 EndIf
 ;advance  
 If VerticalPressed(cyc)
  If cUp(cyc) Then pHurtA#(cyc)=pA#(cyc)
  If cDown(cyc) Then pHurtA#(cyc)=CleanAngle#(pA#(cyc)+180)
  RotateEntity pPivot(cyc),0,pA#(cyc),0
  If pDazed(cyc)>0
   If cUp(cyc) Then MoveEntity pPivot(cyc),0,0,-(speed#/2)
   If cDown(cyc) Then MoveEntity pPivot(cyc),0,0,speed#
  Else 
   If cUp(cyc) Then MoveEntity pPivot(cyc),0,0,speed#
   If cDown(cyc) Then MoveEntity pPivot(cyc),0,0,-(speed#/2)
  EndIf
 EndIf
End Function

;GRIMACE VIABLE?
Function GrimaceViable(cyc)
 viable=1
 If pAnim(cyc)<20 Then viable=0 ;movement
 If pAnim(cyc)=24 Then viable=0 ;examining weapon
 If pAnim(cyc)=>74 And pAnim(cyc)=<75 Then viable=0 ;blocking
 If pAnim(cyc)=>76 And pAnim(cyc)=<77 And pAnimTim(cyc)>130 Then viable=0 ;dead
 If pAnim(cyc)=81 Or pAnim(cyc)=84 Then viable=0 ;lying
 If pAnim(cyc)=92 Or pAnim(cyc)=97 Or pAnim(cyc)=98 Then viable=0 ;sweeping/combing
 If pAnim(cyc)=102 Or pAnim(cyc)=103 Then viable=0 ;sitting/sleeping
 If pAnim(cyc)=130 Then viable=0 ;changed weight 
 If pAnim(cyc)=205 Or pAnim(cyc)=206 Then viable=0 ;grappling
 Return viable
End Function

;VIABLE TO TURN HEAD?
Function HeadViable(cyc)
 viable=0
 If pDazed(cyc)=0
  ;guaranteed states
  If pAnim(cyc)<20 And pPhone(cyc)=0 Then viable=1 ;movement
  If pAnim(cyc)=91 Then viable=1 ;waving
  If pAnim(cyc)=102 
   If pState(cyc)=101 Then viable=1 ;slouching
   If OnComputer(cyc) Then viable=1 ;working at computer
   If cyc=promoActor(1) Or cyc=promoActor(2) Then viable=1 ;talking while sitting
  EndIf
  If pAnim(cyc)=205 And (cyc=promoActor(1) Or cyc=promoActor(2)) Then viable=1 ;grappling
  ;close speech override
  If pAnim(cyc)=0 And pSpeaking(cyc) And pState(cyc)<>3 And pAnim(pFoc(cyc))=0 And pY#(cyc)=>pY#(pFoc(cyc))-1 And pY#(cyc)=<pY#(pFoc(cyc))+1
   If InLine(cyc,p(pFoc(cyc)),5) Then viable=0 
  EndIf
  ;monologue override
  If cyc=promoActor(1) And promoActor(2)=0 Then viable=0
 EndIf
 Return viable
End Function

;VIABLE TO TURN BODY?
Function BodyViable(cyc)
 viable=0
 If pAnim(cyc)=>60 And pAnim(cyc)=<61
  If cDefend(cyc)=0 Or pControl(cyc)=0 Then viable=1 ;shooting
 EndIf
 If pAnim(cyc)=91 Then viable=1 ;waving
 Return viable
End Function

;VIABLE TO UPDATE FOC?
Function FocViable(cyc)
 viable=1
 If pAnim(cyc)=25 Or pAnim(cyc)=26 Then viable=0 ;handing over
 If pAnim(cyc)=60 Or pAnim(cyc)=61 Then viable=0 ;shooting
 If pAnim(cyc)=91 Then viable=0 ;waving
 If cyc=promoActor(1) Or cyc=promoActor(2) Then viable=0 ;speaking
 Return viable
End Function

;COLLAPSE VIABLE?
Function CollapseViable(cyc)
 viable=1
 If pAnim(cyc)=>72 And pAnim(cyc)=<73 Then viable=0 ;ground hurt
 If pAnim(cyc)=>76 And pAnim(cyc)=<77 Then viable=0 ;dying
 If pAnim(cyc)=>80 And pAnim(cyc)=<89 Then viable=0 ;lying
 If pAnim(cyc)=96 Then viable=0 ;breaking down 
 If pAnim(cyc)=>100 And pAnim(cyc)=<101 Then viable=0 ;sitting 
 If pAnim(cyc)=>201 Then viable=0 ;grappling
 Return viable
End Function

;ATTACK VIABLE?
Function AttackViable(cyc) ;1=upper, 2=lower, 3=ground
 ;upper as standard
 viable=1
 ;lower variations
 If pAnim(cyc)=1 Or pAnim(cyc)=11 Then viable=2 ;kneeling
 If pAnim(cyc)=75 Then viable=2 ;lower block
 ;If pAnim(cyc)=>100 And pAnim(cyc)=<109 Then viable=2 ;sitting 
 ;ground variations
 If pAnim(cyc)=72 Or pAnim(cyc)=73 Then viable=3 ;lying hurt
 If pAnim(cyc)=>76 And pAnim(cyc)=<77 And pAnimTim(cyc)>150 Then viable=3 ;lying dead
 If pAnim(cyc)=81 Or pAnim(cyc)=84 Then viable=3 ;lying
 ;exceptions
 If pAnim(cyc)=80 Or pAnim(cyc)=83 Or pAnim(cyc)=86 Then viable=0 ;falling
 If pAnim(cyc)=35 Or pAnim(cyc)=82 Or pAnim(cyc)=85 Then viable=0 ;getting up
 If pAnim(cyc)=90 Then viable=0 ;opening door
 If pAnim(cyc)=96 Then viable=0 ;breaking down 
 If pAnim(cyc)=>100 And pAnim(cyc)=<101 Then viable=0 ;sitting
 If pAnim(cyc)=202 Or pAnim(cyc)=203 Or pAnim(cyc)=204 Or pAnim(cyc)=>210 Then viable=0 ;grappling 
 ;unavailable
 If pAnim(cyc)=>76 And pAnim(cyc)=<77 And pAnimTim(cyc)<150 Then viable=0 ;dying
 ;If charState(pChar(cyc))=0 Then viable=0
 Return viable
End Function

;GROUND HURT REACTION
Function GroundReaction(cyc)
 If pHealth(cyc)>0
  ;lying on back
  If pAnim(cyc)=72 Or pAnim(cyc)=81 Then ChangeAnim(cyc,72)
  ;lying on front
  If pAnim(cyc)=73 Or pAnim(cyc)=84 Then ChangeAnim(cyc,73)
 EndIf
End Function

;SEATED TASKS
Function SittingEffects(cyc)
 ;watching TV
 If gamLocation(slot)=9 And pSeat(cyc)=>1 And pSeat(cyc)=<6 And pState(cyc)=101
  randy=Rnd(0,7500)
  If randy=1 And pChar(cyc)=gamChar(slot) Then charStrength(pChar(cyc))=charStrength(pChar(cyc))-1
  If randy=2 And pChar(cyc)=gamChar(slot) Then charAgility(pChar(cyc))=charAgility(pChar(cyc))-1
  If randy=3 And pChar(cyc)=gamChar(slot) Then charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))-1
  randy=Rnd(0,15000)
  If randy=0 And gamGrowth(slot)=<0 Then gamGrowth(slot)=gamGrowth(slot)+1
  randy=Rnd(0,100)
  If randy=<2 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+1
 EndIf
 ;studying
 If gamLocation(slot)=4 And pState(cyc)=102
  randy=Rnd(0,200)
  If randy=0 
   charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))+1
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
   randy=Rnd(0,5)
   If randy=0 And charReputation(pChar(cyc))>50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   If randy=0 And charReputation(pChar(cyc))<50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
  EndIf
 EndIf
 ;searching computer
 If pChar(cyc)=gamChar(slot) And OnComputer(cyc) And pState(cyc)=109
  If cLeft(cyc)=1 And pAnimTim(cyc)>10 And keytim=0 Then gamFile=gamFile-1 : PlaySound sComputer : keytim=5
  If cRight(cyc)=1 And pAnimTim(cyc)>10 And keytim=0 Then gamFile=gamFile+1 : PlaySound sComputer : keytim=5
  If gamFile<1 Then gamFile=no_chars
  If gamFile>no_chars Then gamFile=1
 EndIf
 ;weight-lifting
 If pState(cyc)=108
  randy=Rnd(0,200)
  If randy=<2 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,Rnd(0.1,0.5))
  If randy=0 
   charStrength(pChar(cyc))=charStrength(pChar(cyc))+1
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
   randy=Rnd(0,5)
   If randy=0 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
   pHealth(cyc)=pHealth(cyc)-1
   randy=Rnd(0,50)
   If randy=0 And gamGrowth(slot)=<0 Then gamGrowth(slot)=gamGrowth(slot)+1
  EndIf 
  If pAnimTim(cyc)=10 Then ProduceSound(p(cyc),sAxe,22050,0)
 EndIf
 ;canteen food
 If pState(cyc)=103
  pFoodTim(cyc)=pFoodTim(cyc)-1
  If pFoodTim(cyc)<0 Then pFoodTim(cyc)=0
  If pAnimTim(cyc)=30 Or pAnimTim(cyc)=40 Then limb=FindChild(p(cyc),"R_Finger04")
  If pAnimTim(cyc)=85 Or pAnimTim(cyc)=95 Then limb=FindChild(p(cyc),"L_Finger04")
  If pAnimTim(cyc)=30 Or pAnimTim(cyc)=85
   ProduceSound(p(cyc),sEat,22050,0)
   CreateSpurt(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),1,5,5) 
   trayState(pSeat(cyc))=trayState(pSeat(cyc))-1
   pFoodTim(cyc)=15 
   If pChar(cyc)=gamChar(slot)
    For tray=1 To 50
     If tray<>pSeat(gamPlayer(slot)) And trayState(tray)>0 Then trayState(tray)=trayState(tray)-Rnd(0,1)
    Next
   EndIf
  EndIf
  If pAnimTim(cyc)=40 Or pAnimTim(cyc)=95
   CreateSpurt(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),1,5,5) 
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
   pHealth(cyc)=pHealth(cyc)+5
   randy=Rnd(0,50)
   If randy=0 And gamGrowth(slot)=<0 Then gamGrowth(slot)=gamGrowth(slot)+1 
  EndIf 
  If pAnimTim(cyc)>120 Then pAnimTim(cyc)=10
 EndIf
 ;workshop kits
 If gamLocation(slot)=10 And pState(cyc)=104
  randy=Rnd(0,30)
  If randy=0  
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(0,1)
   ProduceSound(p(cyc),weapSound(kitType(pSeat(cyc))),22050,Rnd(0.1,0.3))
   ;limb=FindChild(world,"Table"+Dig$(pSeat(cyc),10))
   ;PositionEntity kit(pSeat(cyc)),EntityX(limb,1)+Rnd(-0.10,0.10),EntityY(limb,1),EntityZ(limb,1)+Rnd(-0.10,0.10)
  EndIf
  chance=(150-charStrength(pChar(cyc)))+(150-charIntelligence(pChar(cyc)))
  chance=chance*4
  randy=Rnd(0,chance)
  If randy=0 And pAnimTim(cyc)>50 And kitState(pSeat(cyc))>0
   If pChar(cyc)=gamChar(slot) And LockDown()=0
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+25;weapValue(kitType(pSeat(cyc)))
   EndIf
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5
   charReputation(pChar(cyc))=charReputation(pChar(cyc))+Rnd(0,1)
   CreateWeapon(kitType(pSeat(cyc)),EntityX(kit(pSeat(cyc)),1),EntityY(kit(pSeat(cyc)),1)+5,EntityZ(kit(pSeat(cyc)),1))
   HideEntity kit(pSeat(cyc))
   kitState(pSeat(cyc))=0
  EndIf
 EndIf
 ;working
 If gamLocation(slot)=4 Or gamLocation(slot)=6 Or gamLocation(slot)=8 
  If pChar(cyc)=gamChar(slot) And LockDown()=0 And pState(cyc)=104 And pAnimTim(cyc)>160
   PlaySound sCash : statTim(7)=50
   If gamLocation(slot)=8 Then gamMoney(slot)=gamMoney(slot)+5
   If gamLocation(slot)=4 Then gamMoney(slot)=gamMoney(slot)+10
   If gamLocation(slot)=6 Then gamMoney(slot)=gamMoney(slot)+15
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
   randy=Rnd(0,10)
   If randy=0 And charReputation(pChar(cyc))>50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   If randy=0 And charReputation(pChar(cyc))<50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))+1
   If randy=1 Then charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))+1
   pAnimTim(cyc)=10 
  EndIf
 EndIf
End Function

;GET POWER
Function GetPower(cyc)
 power=1
 If charStrength(pChar(cyc))=>60 Then power=Rnd(1,2)
 If charStrength(pChar(cyc))=>70 Then power=2
 If charStrength(pChar(cyc))=>80 Then power=Rnd(2,3) 
 If charStrength(pChar(cyc))=>90 Then power=3
 Return power
End Function

;GET BLOCKING POTENTIAL
Function BlockPower(cyc) ;1-4
 block=GetPower(cyc)
 If pWeapon(cyc)>0 And weapStyle(weapType(pWeapon(cyc)))=1 Then block=block+1
 Return block
End Function

;ANSWER/HANG-UP PHONE
Function AnswerPhone(cyc,phone,anim)
 If phone>0
  PointEntity pPivot(cyc),FindChild(world,"Pad"+Dig$(phone,10))
  pA#(cyc)=EntityYaw(pPivot(cyc))
  ChangeAnim(cyc,anim) 
 EndIf
End Function