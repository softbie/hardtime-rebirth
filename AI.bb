;//////////////////////////////////////////////////////////////////////////////
;--------------------- HARD TIME: ARTIFICIAL INTELLIGENCE ---------------------
;//////////////////////////////////////////////////////////////////////////////

;----------------------------------------------------------------
;///////////////////// GET INPUT ////////////////////////////////
;----------------------------------------------------------------
Function GetInput(cyc)
 ;reset commands
 cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
 cAttack(cyc)=0 : cDefend(cyc)=0
 cThrow(cyc)=0 : cPickUp(cyc)=0
 ;get keyboard input
 If (pControl(cyc)=1 Or pControl(cyc)=3) And charBreakdown(pChar(cyc))=0
  If KeyDown(200) Then cUp(cyc)=1
  If KeyDown(208) Then cDown(cyc)=1
  If KeyDown(203) Then cLeft(cyc)=1
  If KeyDown(205) Then cRight(cyc)=1
  If KeyDown(keyAttack) Then cAttack(cyc)=1
  If KeyDown(keyDefend) Then cDefend(cyc)=1
  If KeyDown(keyThrow) Then cThrow(cyc)=1
  If KeyDown(keyPickUp) Then cPickUp(cyc)=1
 EndIf
 ;get gamepad input
 If (pControl(cyc)=2 Or pControl(cyc)=3) And charBreakdown(pChar(cyc))=0
  If JoyYDir()=-1 Then cUp(cyc)=1
  If JoyYDir()=1 Then cDown(cyc)=1
  If JoyXDir()=-1 Then cLeft(cyc)=1
  If JoyXDir()=1 Then cRight(cyc)=1
  If JoyDown(buttAttack) Then cAttack(cyc)=1
  If JoyDown(buttDefend) Then cDefend(cyc)=1
  If JoyDown(buttThrow) Then cThrow(cyc)=1
  If JoyDown(buttPickUp) Then cPickUp(cyc)=1
 EndIf
 ;get CPU input
 If pControl(cyc)=0 Or charBreakdown(pChar(cyc))>0
  AI(cyc)
 EndIf
End Function

;----------------------------------------------------------------
;/////////////// ARTIFICIAL INTELLIGENCE ////////////////////////
;----------------------------------------------------------------
Function AI(cyc)
 ;DETERMINE AGENDA
 pOldAgenda(cyc)=pAgenda(cyc)
 If cyc<>promoActor(1) And cyc<>promoActor(2)
  randy=Rnd(0,1000)
  If randy=0 Then pAgenda(cyc)=0
  If randy=1 Then pAgenda(cyc)=1
  If randy=2 Or charPromo(pChar(cyc),gamChar(slot))>0 Or charFollowTim(pChar(cyc))>0 Then pAgenda(cyc)=2 : pFollowFoc(cyc)=0
  If randy=3 Or LockDown()
   If charRole(pChar(cyc))=0 And GetBlock(gamLocation(slot))>0 And charFollowTim(pChar(cyc))=0 Then pAgenda(cyc)=3
  EndIf
  If randy=4 Or (randy=5 And charRole(pChar(cyc))=1)
   If pWeapon(cyc)=0 Then pAgenda(cyc)=4 : pWeapFoc(cyc)=0
  EndIf
 EndIf
 ;avoid conversation area
 If gamPromo>0 And promoActor(1)>0 And promoActor(2)>0 And cyc<>promoActor(1) And cyc<>promoActor(2)
  If pTX#(cyc)>LowestValue#(pX#(promoActor(1)),pX#(promoActor(2))) And pTX#(cyc)<HighestValue#(pX#(promoActor(1)),pX#(promoActor(2))) And pTZ#(cyc)>LowestValue#(pZ#(promoActor(1)),pZ#(promoActor(2))) And pTZ#(cyc)<HighestValue#(pZ#(promoActor(1)),pZ#(promoActor(2)))
   pNowhere(cyc)=99
  EndIf 
 EndIf
 ;rethink if getting nowhere
 If pNowhere(cyc)>30 
  If pSubX#(cyc)=9999 And pSubZ#(cyc)=9999
   pSubX#(cyc)=pX#(cyc)+Rnd(-200,200)
   pSubZ#(cyc)=pZ#(cyc)+Rnd(-200,200) 
  Else
   pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
  EndIf
  pAgenda(cyc)=1
 EndIf
 ;EXECUTE AGENDA
 ;contemplate
 If pAgenda(cyc)=0
  randy=Rnd(0,200)
  If randy=<1 And pAnim(cyc)<20
   Repeat
    pTA#(cyc)=Rnd(0,360)
   Until SatisfiedAngle(pA#(cyc),pTA#(cyc),10)=0
  EndIf
  pTX#(cyc)=pX#(cyc) : pTY#(cyc)=pY#(cyc) : pTZ#(cyc)=pZ#(cyc)
  pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
  pExploreRange(cyc)=5
 EndIf
 ;explore
 If pAgenda(cyc)=1
  randy=Rnd(0,500)
  If randy=<1 Or pNowhere(cyc)>30 Or pAgenda(cyc)<>pOldAgenda(cyc)
   If GetBlock(gamLocation(slot))>0
    pExploreX#(cyc)=Rnd(-290,290) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-330,350)
    randy=Rnd(0,1)
    If randy=0
     Repeat
      pExploreX#(cyc)=Rnd(-290,290) : pExploreY#(cyc)=105 : pExploreZ#(cyc)=Rnd(-140,350)
     Until pExploreX#(cyc)<-150 Or pExploreX#(cyc)>150 Or pExploreZ#(cyc)>210
    EndIf
    If pExploreX#(cyc)>-50 And pExploreX#(cyc)<50 And pExploreZ#(cyc)>20 And pExploreZ#(cyc)<210 Then pExploreY#(cyc)=9999
   EndIf
   If gamLocation(slot)=2
    Repeat
     pExploreX#(cyc)=Rnd(-25,485) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-45,490)
    Until pExploreX#(cyc)>200 Or pExploreZ#(cyc)>200
   EndIf
   If gamLocation(slot)=4 Or gamLocation(slot)=6 Then pExploreX#(cyc)=Rnd(-140,140) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-140,140)
   If gamLocation(slot)=8 Then pExploreX#(cyc)=Rnd(-255,255) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-340,330)
   If gamLocation(slot)=9
    pExploreX#(cyc)=Rnd(-295,295) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-295,295)
    randy=Rnd(0,5)
    If randy=0 Or (randy=1 And phoneRing>0) Then pExploreX#(cyc)=Rnd(-295,-260) : pExploreZ#(cyc)=Rnd(-50,70)
   EndIf
   If gamLocation(slot)=10 Then pExploreX#(cyc)=Rnd(-95,95) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-115,115) 
   If gamLocation(slot)=11 Then pExploreX#(cyc)=Rnd(-140,140) : pExploreY#(cyc)=10 : pExploreZ#(cyc)=Rnd(-65,70) 
   pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
   pNowhere(cyc)=0 
  EndIf
  pTX#(cyc)=pExploreX#(cyc)
  pTY#(cyc)=pExploreY#(cyc)
  pTZ#(cyc)=pExploreZ#(cyc)
  pExploreRange(cyc)=5
  If InsideCell(pX#(cyc),pY#(cyc),pZ#(cyc))=charCell(pChar(cyc)) And cellLocked(gamLocation(slot),charCell(pChar(cyc))) Then pAgenda(cyc)=3
 EndIf
 ;follow
 If pAgenda(cyc)=2
  If no_plays>1
   alert=0 : v=pFoc(gamPlayer(slot))
   If v>0 And v<>cyc And (charAngerTim(gamChar(slot),pChar(v))>0 Or charAngerTim(pChar(v),gamChar(slot))>0)
    alert=1
    If charAngerTim(pChar(cyc),pChar(v))<10 Then charAngerTim(pChar(cyc),pChar(v))=10 
   EndIf
   If charFollowTim(pChar(cyc))>0 And alert=0 Then pFollowFoc(cyc)=gamPlayer(slot)
   If charFollowTim(pChar(cyc))>0 And alert=1 Then pFollowFoc(cyc)=pFoc(gamPlayer(slot))
   If charPromo(pChar(cyc),gamChar(slot))>0 And gamPromo=0 Then pFollowFoc(cyc)=gamPlayer(slot)
   While pFollowFoc(cyc)=0 Or pFollowFoc(cyc)=cyc
    pFollowFoc(cyc)=Rnd(1,no_plays)
    randy=Rnd(0,1) : its=0
    If randy=0
     While Friendly(cyc,pFollowFoc(cyc))=0 And its<100
      pFollowFoc(cyc)=Rnd(1,no_plays)
      its=its+1
     Wend
    EndIf
   Wend
   pTX#(cyc)=pX#(pFollowFoc(cyc))
   pTY#(cyc)=pY#(pFollowFoc(cyc))
   pTZ#(cyc)=pZ#(pFollowFoc(cyc))
   If pOldAgenda(cyc)<>2 Then pExploreRange(cyc)=Rnd(15,50)
   If pExploreRange(cyc)<10 Then pExploreRange(cyc)=10
   If charAngerTim(pChar(cyc),pChar(pFollowFoc(cyc)))>0 Then pExploreRange(cyc)=10
  Else
   pAgenda(cyc)=1
  EndIf
 EndIf
 ;enter cell
 If pAgenda(cyc)=3
  randy=Rnd(0,500)
  cell=charCell(pChar(cyc))
  If randy=<1 Or pAgenda(cyc)<>pOldAgenda(cyc) Or InsideCell(pExploreX#(cyc),pExploreY#(cyc),pExploreZ#(cyc))<>cell
   pExploreX#(cyc)=Rnd(cellX1#(cell),cellX2#(cell))
   pExploreY#(cyc)=cellY1#(cell)+10
   pExploreZ#(cyc)=Rnd(cellZ1#(cell),cellZ2#(cell))
   pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
   pNowhere(cyc)=0 
  EndIf
  pTX#(cyc)=pExploreX#(cyc)
  pTY#(cyc)=pExploreY#(cyc)
  pTZ#(cyc)=pExploreZ#(cyc)
  pExploreRange(cyc)=5
 EndIf
 ;pursue weapon
 If pAgenda(cyc)=4
  its=0
  If pWeapFoc(cyc)=0 Or weapLocation(pWeapFoc(cyc))<>gamLocation(slot)
   Repeat 
    pWeapFoc(cyc)=Rnd(1,no_weaps)
    randy=Rnd(0,1)
    If randy=0 And weapCarrier(pWeapFoc(cyc))>0 Then satisfied=0
    If weapCarrier(pWeapFoc(cyc))>0 And Friendly(cyc,weapCarrier(pWeapFoc(cyc)))=0 Then satisfied=0
    its=its+1
    If its>100 Then pWeapFoc(cyc)=0 : satisfied=1
   Until satisfied=1
  EndIf
  If pWeapFoc(cyc)>0 And pWeapon(cyc)<>pWeapFoc(cyc) 
   pTX#(cyc)=weapX#(pWeapFoc(cyc))
   pTZ#(cyc)=weapZ#(pWeapFoc(cyc))
   pExploreRange(cyc)=5
  Else
   pAgenda(cyc)=1
  EndIf
 EndIf
 ;CONSIDER SUB-ROUTES
 ;reset once satisified
 If Reached(pX#(cyc),pSubX#(cyc),5) Then pSubX#(cyc)=9999
 If Reached(pZ#(cyc),pSubZ#(cyc),5) Then pSubZ#(cyc)=9999
 ;filter current sub-route
 If pSubX#(cyc)<>9999 Then pTX#(cyc)=pSubX#(cyc)
 If pSubZ#(cyc)<>9999 Then pTZ#(cyc)=pSubZ#(cyc)
 If GetBlock(gamLocation(slot))>0
  ;get into cell
  current=InsideCell(pX#(cyc),pY#(cyc),pZ#(cyc))
  target=InsideCell(pTX#(cyc),pTY#(cyc),pTZ#(cyc))
  If current=0 And target>0 
   If ReachedCord(pX#(cyc),pZ#(cyc),cellDoorX#(target),cellDoorZ#(target),30)=0
    pSubX#(cyc)=cellDoorX#(target) : pSubZ#(cyc)=cellDoorZ#(target)
   EndIf
  EndIf
  ;get out of cell
  If current>0 And target<>current
   If ReachedCord(pX#(cyc),pZ#(cyc),cellDoorX#(current),cellDoorZ#(current),30)=0
    pSubX#(cyc)=cellDoorX#(current) : pSubZ#(cyc)=cellDoorZ#(current)
   EndIf
  EndIf
  ;head upstairs
  If pY#(cyc)<100 And pTY#(cyc)>100 And pTY#(cyc)<9999
   If pX#(cyc)<-50 Or pX#(cyc)>50
    If pSubX#(cyc)<-40 Or pSubX#(cyc)>40 Then pSubX#(cyc)=Rnd(-40,40)
    If pZ#(cyc)>10 Then pSubZ#(cyc)=10
   EndIf
   If pX#(cyc)>-50 And pX#(cyc)<50 And (pTX#(cyc)<-50 Or pTX#(cyc)>50)
    If pSubX#(cyc)<-40 Or pSubX#(cyc)>40 Then pSubX#(cyc)=Rnd(-40,40)
    If pTZ#(cyc)<220 Then pSubZ#(cyc)=220
   EndIf
  EndIf
  ;head downstairs
  If pY#(cyc)>100 And pTY#(cyc)<100 And pTY#(cyc)<9999
   If pX#(cyc)<-50 Or pX#(cyc)>50
    If pSubX#(cyc)<-40 Or pSubX#(cyc)>40 Then pSubX#(cyc)=Rnd(-40,40)
    If pZ#(cyc)<220 Then pSubZ#(cyc)=220
   EndIf
   If pX#(cyc)>-50 And pX#(cyc)<50 And (pTX#(cyc)<-50 Or pTX#(cyc)>50)
    If pSubX#(cyc)<-40 Or pSubX#(cyc)>40 Then pSubX#(cyc)=Rnd(-40,40)
    If pTZ#(cyc)>10 Then pSubZ#(cyc)=10
   EndIf
  EndIf
  ;negotiate balcony
  If pY#(cyc)>100 And pTY#(cyc)>100 And pTY#(cyc)<9999 And pZ#(cyc)<220 And pTZ#(cyc)<220
   If pX#(cyc)>-50 And pX#(cyc)<50 And (pTX#(cyc)<-50 Or pTX#(cyc)>50) Then pSubZ#(cyc)=220
   If pX#(cyc)>-150 And pX#(cyc)<150 And (pTX#(cyc)<-150 Or pTX#(cyc)>150) Then pSubZ#(cyc)=220
   If (pX#(cyc)<-150 And pTX#(cyc)>150) Or (pX#(cyc)>150 And pTX#(cyc)<-150) Then pSubZ#(cyc)=220
  EndIf 
  ;get around stairs
  If pY#(cyc)<100 And pZ#(cyc)>10 And pTZ#(cyc)>10
   If pX#(cyc)<-45 And pTX#(cyc)>45 Then pSubX#(cyc)=-40 : pSubZ#(cyc)=10
   If pX#(cyc)>45 And pTX#(cyc)<-45 Then pSubX#(cyc)=40 : pSubZ#(cyc)=10
  EndIf
  ;get off stairs
  If pTY#(cyc)<100 And pX#(cyc)=>-40 And pX#(cyc)=<40 And pZ#(cyc)>10 And pTZ#(cyc)>10
   If pTX#(cyc)<-45 Or pTX#(cyc)>45 Then pSubZ#(cyc)=10
  EndIf
 EndIf
 If gamLocation(slot)=8
  ;get out from behind kitchen counter 
  If pX#(cyc)<-145 And pZ#(cyc)<145
   If pTX#(cyc)>-145 Or pTZ#(cyc)>145 Then pSubX#(cyc)=-215 : pSubZ#(cyc)=160
  EndIf
  ;get in behind kitchen counter 
  If pTX#(cyc)<-145 And pTZ#(cyc)<165
   If pX#(cyc)>-125 Then pSubX#(cyc)=-215 : pSubZ#(cyc)=180
   If pZ#(cyc)>165 And pTX#(cyc)>-190 Then pSubX#(cyc)=-215 : pSubZ#(cyc)=180
  EndIf
 EndIf
 ;MOVEMENT INPUT
 ;pursue sub-route
 If pSubX#(cyc)<>9999 Then pTX#(cyc)=pSubX#(cyc) : pExploreRange(cyc)=5
 If pSubZ#(cyc)<>9999 Then pTZ#(cyc)=pSubZ#(cyc) : pExploreRange(cyc)=5
 ;update target angle
 If ReachedCord(pX#(cyc),pZ#(cyc),pTX#(cyc),pTZ#(cyc),pExploreRange(cyc)) And ReachedHeight(pY#(cyc),pTY#(cyc),20) 
  pSatisfied(cyc)=20
  pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
 Else
  PositionEntity dummy,pTX#(cyc),pY#(cyc),pTZ#(cyc)
  PointEntity p(cyc),dummy
  pTA#(cyc)=CleanAngle#(EntityYaw(p(cyc))) 
  RotateEntity p(cyc),0,pA#(cyc),0 
  cUp(cyc)=1
  If ReachedCord(pX#(cyc),pZ#(cyc),pTX#(cyc),pTZ#(cyc),20) And SatisfiedAngle(pA#(cyc),pTA#(cyc),25)=0 Then cUp(cyc)=0
  If pAgenda(cyc)=2 And pSatisfied(cyc)>0 Then cUp(cyc)=0
 EndIf
 ;turn
 If SatisfiedAngle(pA#(cyc),pTA#(cyc),15)=0
  If ReachAngle#(pA#(cyc),pTA#(cyc),1)>0 Then cLeft(cyc)=1
  If ReachAngle#(pA#(cyc),pTA#(cyc),1)<0 Then cRight(cyc)=1
 EndIf  
 ;override when grappling 
 If pGrappling(cyc)>0
  cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
 EndIf
 ;running
 If pAnim(cyc)=>12 And pAnim(cyc)=<13 And VerticalPressed(cyc)
  randy=Rnd(0,500)
  If ReachedCord(pX#(cyc),pZ#(cyc),pTX#(cyc),pTZ#(cyc),200)=0 Then randy=Rnd(0,50)
  If pAgenda(cyc)=2 And charAngerTim(pChar(cyc),pChar(pFoc(cyc)))>0
   randy=Rnd(0,50)
   If pAnim(pFoc(cyc))=13 Then randy=0
  EndIf
  If randy=<1 And pRunTim(cyc)=0 Then pRunTim(cyc)=Rnd(25,200)
  If ReachedCord(pX#(cyc),pZ#(cyc),pTX#(cyc),pTZ#(cyc),10) Then pRunTim(cyc)=0
  If pRunTim(cyc)>0 Then cDefend(cyc)=1
 EndIf
 ;WEAPON INTERACTION
 ;attracted to nearby item
 v=NearestWeapon(cyc)
 If v>0 And pWeapon(cyc)=0 And HandIntact(cyc,17) And WeaponProximity(cyc,v,50) And pSeat(cyc)=0 And pBed(cyc)=0 And pNowhere(cyc)=0
  randy=Rnd(0,500)
  If weapCarrier(v)>0 Then randy=Rnd(0,5000)
  If charRole(pChar(cyc))=1 And ((weapType(v)=>7 And weapType(v)=<9) Or weapType(v)=12) Then randy=Rnd(0,50)
  If randy=0 And pAgenda(cyc)<>4 Then pAgenda(cyc)=4 : pWeapFoc(cyc)=v : pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
 EndIf
 ;pick up nearby item
 range=weapSize#(weapType(v))+5
 If weapY#(v)>pY#(cyc)+10 Then range=range+10
 If v>0 And pWeapon(cyc)=0 And weapCarrier(v)=0 And WeaponProximity(cyc,v,range) And Isolated(cyc,20) And HandIntact(cyc,17) And pSeat(cyc)=0 And pBed(cyc)=0
  randy=Rnd(0,30)
  If weapStyle(weapType(v))=>3 And weapStyle(weapType(v))=<4 And weapAmmo(v)=0 Then randy=Rnd(0,100)
  If randy=0 Or v=pWeapFoc(cyc) Then cPickUp(cyc)=1
 EndIf
 ;snatch enemy's weapon
 v=pFoc(cyc)
 If v>0 And pWeapon(cyc)=0 And pWeapon(v)>0 And Friendly(cyc,v)=0 And charGang(pChar(cyc))<>6 And InProximity(cyc,v,25) And AttackViable(v)=>1 And AttackViable(v)=<2 And HandIntact(cyc,17)
  randy=Rnd(0,5000)
  If charRole(pChar(cyc))=1 Or charAngerTim(pChar(cyc),pChar(v))>0 Then randy=Rnd(0,100)
  If randy=0 Or pWeapon(v)=pWeapFoc(cyc) Then cPickUp(cyc)=1
 EndIf
 ;release current weapon
 If pWeapon(cyc)>0
  randy=Rnd(0,5000)
  If weapStyle(weapType(pWeapon(cyc)))=>3 And weapStyle(weapType(pWeapon(cyc)))=<4 And weapAmmo(pWeapon(cyc))>0
   randy=Rnd(0,10000)
   If charRole(pChar(cyc))=1 Then randy=999
  EndIf
  If charRole(pChar(cyc))=0 And weapHabitat(weapType(pWeapon(cyc)))<99 And weapHabitat(weapType(pWeapon(cyc)))<>gamLocation(slot)
   For v=1 To no_plays
    If charRole(pChar(v))=1 And InProximity(v,cyc,20)
     If InLine(cyc,p(v),60) And InLine(v,p(cyc),60) Then randy=Rnd(0,100-charIntelligence(pChar(cyc)))
    EndIf
   Next
  EndIf
  If randy=0 Or weapAmmo(pWeapon(cyc))=0 Then cPickUp(cyc)=1 : cThrow(cyc)=0 ;drop
  If InProximity(cyc,pFoc(cyc),150) And InProximity(cyc,pFoc(cyc),30)=0
   If randy=>1 And randy=<3 Then cThrow(cyc)=1 : cPickUp(cyc)=0 ;throw
   If randy=>4 And randy=<6 And weapStyle(weapType(pWeapon(cyc)))=7 Then cThrow(cyc)=1 : cPickUp(cyc)=0 ;spear throw
   If randy=<20 And NearBasket(cyc) Then cThrow(cyc)=1 : cPickUp(cyc)=0 ;basketball
   If randy=<20 And weapStyle(weapType(pWeapon(cyc)))=6 Then cThrow(cyc)=Rnd(0,1) : cPickUp(cyc)=Rnd(0,1) ;explosives
  EndIf
 EndIf
 ;answer phone
 v=PhoneProximity(cyc)
 If v>0 And PhoneTaken(v)=0 And pPhone(cyc)=0 And Isolated(cyc,30)
  randy=Rnd(0,200)
  If v=phoneRing Then randy=Rnd(0,50)
  If randy=0 Then cPickUp(cyc)=1 : cThrow(cyc)=0
 EndIf
 ;ATTACKING
 ;find victims
 v=pFoc(cyc)
 If v>0 And (charAngerTim(pChar(cyc),pChar(v))>0 Or charBreakdown(pChar(cyc))>0) And AttackViable(v)>0 And pHealth(v)>0 And charGang(pChar(cyc))<>6
  ;assess range 
  range=22
  If pWeapon(cyc)>0 Then range=range+(weapRange#(weapType(pWeapon(cyc)))*2)
  If AttackViable(v)=3 Then range=range-(range/3) 
  If weapStyle(weapType(pWeapon(cyc)))=>3 And weapStyle(weapType(pWeapon(cyc)))=<4 Then range=500
  ;assess intensity
  intensity=(100-charReputation(pChar(cyc)))*3
  If charBreakdown(pChar(cyc))>0 Then intensity=intensity/2
  If intensity<10 Then intensity=10
  If pHealth(v)=<5 Then intensity=intensity*5
  If range=>250 And InProximity(cyc,v,30)=0 Then intensity=intensity*10
  ;basic attacks
  If InProximity(cyc,v,range)
   randy=Rnd(0,intensity)
   If randy=<4 Then cAttack(cyc)=1 : cDefend(cyc)=0 : cThrow(cyc)=0
   If randy=>5 And randy=<6 Then cAttack(cyc)=1 : cDefend(cyc)=1 : cThrow(cyc)=0
   If randy=>7 And randy=<8 And InProximity(cyc,v,30) And pGrappling(v)=0 And pGrappler(v)=0 And pSeat(v)=0 And pBed(v)=0 
    cAttack(cyc)=0 : cDefend(cyc)=0 : cThrow(cyc)=1
   EndIf
  EndIf
  ;force low
  If cAttack(cyc)=1 And AttackViable(v)=2
   cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
  EndIf
  ;force rear
  If cAttack(cyc)=1 And AttackViable(v)=1 And InProximity(cyc,v,50)
   If InLine(cyc,p(v),115)=0 Then cAttack(cyc)=1 : cThrow(cyc)=1
  EndIf
  ;don't shoot if not in sight
  If cAttack(cyc)=1 And weapStyle(weapType(pWeapon(cyc)))=>3 And weapStyle(weapType(pWeapon(cyc)))=<4
   If InLine(cyc,p(v),60)=0 Then cAttack(cyc)=0
  EndIf
 EndIf
 ;DEFENDING
 intensity=(100-charIntelligence(pChar(cyc)))*3
 If intensity<10 Then intensity=10 
 If charBreakdown(pChar(cyc))>0 Then intensity=intensity*5
 If AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And cThrow(cyc)=0
  For v=1 To no_plays
   If cyc<>v And cyc=pFoc(v) And charAngerTim(pChar(cyc),pChar(v))>0
    ;hand-to-hand threats
    range=30
    If pWeapon(v)>0 Then range=range+(weapRange#(weapType(pWeapon(v)))*2)
    If InProximity(cyc,v,range) And pAnim(v)=>30 And pAnim(v)=<49 And pAnim(v)<>35 And pSting(v)=1
     If InLine(v,p(cyc),90)
      randy=Rnd(0,intensity) 
      If randy=<5 Or (randy=<10 And InProximity(cyc,v,range/2)) Or (pAnim(cyc)=>74 And pAnim(cyc)=<75)
       cDefend(cyc)=1 : cAttack(cyc)=0
       cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
      EndIf
     EndIf
    EndIf 
    ;gun threats
    If InProximity(cyc,v,500) And pAnim(v)=>60 And pAnim(v)=<61 ;And pFireTim(v)=>5
     randy=Rnd(0,intensity) 
     If randy=<10 Or (pAnim(cyc)=>74 And pAnim(cyc)=<75)
      cDefend(cyc)=1 : cAttack(cyc)=0
      cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
     EndIf
    EndIf
   EndIf  
  Next
 EndIf
End Function

;----------------------------------------------------------------
;////////////////// TRANSLATE INPUT /////////////////////////////
;----------------------------------------------------------------
Function TranslateInput(cyc)
 ;declaw
 If gamPromo>0 Or (cyc=gamPlayer(slot) And promoUsed(71)=0)
  cAttack(cyc)=0 : cDefend(cyc)=0
  cThrow(cyc)=0 : cPickUp(cyc)=0
  If cyc=promoActor(1) Or cyc=promoActor(2) Or (gamMission(slot)=18 And pChar(cyc)=gamClient(slot)) Or (cyc=gamPlayer(slot) And promoUsed(71)=0)
   cUp(cyc)=0 : cDown(cyc)=0 : cLeft(cyc)=0 : cRight(cyc)=0
  EndIf
 EndIf
 ;force turn
 If gamPromo>0 And pAnim(cyc)<20 And pDazed(cyc)=0 
  If cyc=promoActor(1)
   PositionEntity dummy,pX#(cyc),pY#(cyc),pZ#(cyc)
   If promoActor(2)>0
    PointEntity dummy,p(promoActor(2))
    pTA#(cyc)=CleanAngle#(EntityYaw(dummy))
   EndIf
   If promoActor(2)<0
    PointEntity dummy,FindChild(world,"Pad"+Dig$(MakePositive#(promoActor(2)),10))
    pTA#(cyc)=CleanAngle#(EntityYaw(dummy)+180)
   EndIf
  EndIf
  If cyc=promoActor(2)
   PositionEntity dummy,pX#(cyc),pY#(cyc),pZ#(cyc)
   If promoActor(1)>0
    PointEntity dummy,p(promoActor(1))
    pTA#(cyc)=CleanAngle#(EntityYaw(dummy))
   EndIf
   If promoActor(1)<0
    PointEntity dummy,FindChild(world,"Pad"+Dig$(MakePositive#(promoActor(1)),10))
    pTA#(cyc)=CleanAngle#(EntityYaw(dummy)+180)
   EndIf
  EndIf
  If SatisfiedAngle(pA#(cyc),pTA#(cyc),15)=0
   If ReachAngle#(pA#(cyc),pTA#(cyc),1)>0 Then cLeft(cyc)=1
   If ReachAngle#(pA#(cyc),pTA#(cyc),1)<0 Then cRight(cyc)=1
  EndIf 
 EndIf
 ;turn
 If pAnim(cyc)<10 And HorizontalPressed(cyc) And VerticalPressed(cyc)=0
  If pAnim(cyc)<>1 Then ChangeAnim(cyc,10)
  If pAnim(cyc)=1 Then ChangeAnim(cyc,11)
 EndIf
 ;advance
 If pAnim(cyc)<12 And VerticalPressed(cyc) Then ChangeAnim(cyc,12)
 ;attacks
 If cAttack(cyc)=1 And pAnim(cyc)<20 And pDazed(cyc)=0 
  If DirPressed(cyc) Then ChangeAnim(cyc,30) ;upper attack
  If DirPressed(cyc)=0 Then ChangeAnim(cyc,31) ;lower attack
  If cDefend(cyc)=1 Then ChangeAnim(cyc,33) ;big attack
  If AttackViable(pFoc(cyc))=3 Then ChangeAnim(cyc,32) ;stomp
  If cThrow(cyc)=1 Then ChangeAnim(cyc,34) ;rear attack
  If pWeapon(cyc)>0 And InProximity(cyc,pFoc(cyc),25)=0 And cThrow(cyc)=0
   If weapStyle(weapType(pWeapon(cyc)))=3 Then ChangeAnim(cyc,61) ;pistol
   If weapStyle(weapType(pWeapon(cyc)))=4 Then ChangeAnim(cyc,60) ;machine gun
  EndIf
 EndIf
 ;blocks
 If cDefend(cyc)=1 And DirPressed(cyc)=0 And pAnim(cyc)<20 And pDazed(cyc)=0  
  If pAnim(cyc)=1 Or pAnim(cyc)=11 Then blocker=75 Else blocker=74
  ChangeAnim(cyc,blocker)
 EndIf
 ;weapon interaction
 If cPickUp(cyc)=1 And pAnim(cyc)<20 And pDazed(cyc)=0  
  nearest=NearestWeapon(cyc) 
  picker=0
  If nearest>0 And WeaponProximity(cyc,nearest,20) And weapCarrier(nearest)=0 And pWeapon(cyc)=0 Then picker=1
  phone=PhoneProximity(cyc)
  If picker=0 And phone>0 And PhoneTaken(phone)=0 And pPhone(cyc)=0
   AnswerPhone(cyc,phone,28)
  Else
   If pPhone(cyc)>0 Then AnswerPhone(cyc,pPhone(cyc),29)
   If pWeapon(cyc)>0 And pPhone(cyc)=0 Then ChangeAnim(cyc,21)
   If pWeapon(cyc)=0 And pPhone(cyc)=0
    If nearest>0
     PointEntity pPivot(cyc),weap(nearest)
     pA#(cyc)=EntityYaw(pPivot(cyc))
    EndIf
    anim=20
    v=weapCarrier(nearest)
    If nearest>0 And v>0 And AttackViable(v)=>1 And AttackViable(v)=<2 Then anim=23
    If nearest>0 And weapY#(nearest)>pY#(cyc)+10 Then anim=23
    ChangeAnim(cyc,anim)
   EndIf
  EndIf
 EndIf
 ;throw/grab
 If cThrow(cyc)=1 And pAnim(cyc)<20 And pDazed(cyc)=0   
  If pWeapon(cyc)>0 And (InProximity(cyc,pFoc(cyc),30)=0 Or NearBasket(cyc))
   If NearBasket(cyc)
    ChangeAnim(cyc,27)
   Else
    ChangeAnim(cyc,22)
   EndIf
  Else
   If AttackViable(pFoc(cyc))<>3 Then ChangeAnim(cyc,200)
   If AttackViable(pFoc(cyc))=3 Then ChangeAnim(cyc,201)
  EndIf
 EndIf
 ;automatic hang-up
 If pPhone(cyc)>0 And PhoneProximity(cyc)<>pPhone(cyc) And pAnim(cyc)<>29
  If pAnim(cyc)<20 
   AnswerPhone(cyc,pPhone(cyc),29)
  Else
   ProduceSound(p(cyc),sPhone,22050,0)
   HideEntity FindChild(p(cyc),"Phone")
   ShowEntity FindChild(world,"Phone"+Dig$(pPhone(cyc),10))
   pPhone(cyc)=0
  EndIf 
 EndIf
 ;chair interaction
 If no_chairs>0 And pAnim(cyc)<20 And pDazed(cyc)=0
  For chair=1 To no_chairs
   pSeatFriction(cyc,chair)=pSeatFriction(cyc,chair)-1 
   If pSeatFriction(cyc,chair)<0 Then pSeatFriction(cyc,chair)=0
   If ChairProximity(cyc,chair) And ChairTaken(chair)=0
    If cUp(cyc) Then pSeatFriction(cyc,chair)=pSeatFriction(cyc,chair)+2 
   EndIf
   If pSeatFriction(cyc,chair)>10
    ChangeAnim(cyc,100) : pSeat(cyc)=chair
    pLeaveX#(cyc)=pX#(cyc) : pLeaveZ#(cyc)=pZ#(cyc)
    pLeaveY#(cyc)=pY#(cyc)+5 : pLeaveA#(cyc)=CleanAngle#(pA#(cyc)+180)
    If gamLocation(slot)=11
     ResetDummy(cyc)
     MoveEntity dummy,0,0,-5
     pLeaveX#(cyc)=EntityX(dummy) : pLeaveZ#(cyc)=EntityZ(dummy)
     pLeaveY#(cyc)=pY#(cyc)+5 : pLeaveA#(cyc)=CleanAngle#(pA#(cyc)+180)
    EndIf
   EndIf
  Next
 EndIf
 ;bed interaction
 If no_beds>0 And pAnim(cyc)<20 And pDazed(cyc)=0
  For bed=1 To no_beds
   pBedFriction(cyc,bed)=pBedFriction(cyc,bed)-1 
   If pBedFriction(cyc,bed)<0 Then pBedFriction(cyc,bed)=0
   If BedProximity(cyc,bed) And BedTaken(bed)=0
    If cUp(cyc) Then pBedFriction(cyc,bed)=pBedFriction(cyc,bed)+2 
   EndIf
   If pBedFriction(cyc,bed)>10
    ChangeAnim(cyc,100) : pBed(cyc)=bed
    ResetDummy(cyc)
    MoveEntity dummy,0,0,-5
    pLeaveX#(cyc)=EntityX(dummy) : pLeaveZ#(cyc)=EntityZ(dummy)
    pLeaveY#(cyc)=pY#(cyc)+5 : pLeaveA#(cyc)=CleanAngle#(pA#(cyc)+180)
   EndIf
  Next
 EndIf
 ;leave bed/seat if interrupted
 If pSeat(cyc)>0 Or pBed(cyc)>0
  If pAnim(cyc)<100 Or pAnim(cyc)>110 Then ChangeAnim(cyc,101)
 EndIf
 ;door interaction
 If pChar(cyc)=gamChar(slot) And gamDoor=0 And pAnim(cyc)<20 And pDazed(cyc)=0
  For door=1 To no_doors
   pDoorFriction(cyc,door)=pDoorFriction(cyc,door)-1 
   If pDoorFriction(cyc,door)<0 Then pDoorFriction(cyc,door)=0
   If pX#(cyc)>doorX1#(gamLocation(slot),door) And pX#(cyc)<doorX2#(gamLocation(slot),door) And pY#(cyc)>doorY1#(gamLocation(slot),door) And pY#(cyc)<doorY2#(gamLocation(slot),door) And pZ#(cyc)>doorZ1#(gamLocation(slot),door) And pZ#(cyc)<doorZ2#(gamLocation(slot),door) 
    If cUp(cyc) And SatisfiedAngle(pA#(cyc),doorA#(gamLocation(slot),door),45) Then pDoorFriction(cyc,door)=pDoorFriction(cyc,door)+2
   EndIf
   If pDoorFriction(cyc,door)>10 Then ChangeAnim(cyc,90) : gamDoor=door
  Next
 EndIf
End Function

;--------------------------------------------------------------
;////////////////// RELATED FUNCTIONS /////////////////////////
;--------------------------------------------------------------

;DIRECTION PRESSED?
Function DirPressed(cyc)
 value=0
 If cUp(cyc)=1 Or cDown(cyc)=1 Or cLeft(cyc)=1 Or cRight(cyc)=1 Then value=1
 Return value
End Function

;VERTICAL PRESSED?
Function VerticalPressed(cyc)
 value=0
 If cUp(cyc)=1 Or cDown(cyc)=1 Then value=1
 Return value
End Function

;HORIZONTAL PRESSED?
Function HorizontalPressed(cyc)
 value=0
 If cLeft(cyc)=1 Or cRight(cyc)=1 Then value=1
 Return value
End Function

;ACTION PRESSED?
Function ActionPressed(cyc)
 value=0
 If cAttack(cyc)=1 Or cDefend(cyc)=1 Or cThrow(cyc)=1 Or cPickUp(cyc)=1 Then value=1
 Return value
End Function

;BUTTON PRESSED?
Function ButtonPressed()
 value=0
 For count=1 To 12
  If JoyDown(count) Then value=1
 Next
 Return value
End Function

;ENFORCE BLOCKS
Function EnforceBlocks(cyc)
 ;enemy bumping
 For v=1 To no_plays
  If cyc<>v And pGrappling(cyc)<>v And pGrappler(cyc)<>v
   width=8 : height=35
   checkX#=pX#(v) : checkZ#=pZ#(v)
   If pGrappler(v)>0 Or pAnim(v)=>210 Then checkX#=EntityX(pLimb(v,30),1) : checkZ#=EntityZ(pLimb(v,30),1)
   If pOldX#(cyc)>checkX#-width And pOldX#(cyc)<checkX#+width And pOldZ#(cyc)>checkZ#-width And pOldZ#(cyc)<checkZ#+width
    trapped=1
   Else
    If pX#(cyc)>checkX#-width And pX#(cyc)<checkX#+width And pZ#(cyc)>checkZ#-width And pZ#(cyc)<checkZ#+width And pY#(cyc)>pY#(v)-height And pY#(cyc)<pY#(v)+height
     If pOldX#(cyc)>checkX#-width And pOldX#(cyc)<checkX#+width Then pZ#(cyc)=pOldZ#(cyc)
     If pOldZ#(cyc)>checkZ#-width And pOldZ#(cyc)<checkZ#+width Then pX#(cyc)=pOldX#(cyc)
     PositionEntity pPivot(cyc),pX#(cyc),EntityY(pPivot(cyc)),pZ#(cyc)
     If pAnim(cyc)<20 And VerticalPressed(cyc) Then pNowhere(cyc)=pNowhere(cyc)+2
    EndIf
   EndIf
  EndIf
 Next
 ;clock nowhere
 ranger#=pSpeed#(cyc)/2
 If pX#(cyc)>pOldX#(cyc)-ranger# And pX#(cyc)<pOldX#(cyc)+ranger# And pZ#(cyc)>pOldZ#(cyc)-ranger# And pZ#(cyc)<pOldZ#(cyc)+ranger#
  If pAnim(cyc)<20 And VerticalPressed(cyc) Then pNowhere(cyc)=pNowhere(cyc)+2
 EndIf 
End Function

;REACHED HEIGHT?
Function ReachedHeight(y#,tY#,range)
 value=0
 If y#=>tY#-range And y#=<tY#+range Then value=1
 If tY#=9999 Then value=1
 Return value
End Function

;FIND NEAREST ENEMY
Function NearestEnemy(cyc) 
 value=0 : hi#=9999
 If cyc>0
  For v=1 To no_plays 
   distance#=GetDistance#(pX#(cyc),pZ#(cyc),pX#(v),pZ#(v)) 
   If pY#(v)<pY#(cyc)-30 Or pY#(v)>pY#(cyc)+30 Then distance#=distance#+100
   If charAngerTim(pChar(cyc),pChar(v))>0 Then distance#=distance#/2
   If pAgenda(cyc)=2 And v=pFollowFoc(cyc) Then distance#=distance#/2
   If Friendly(cyc,v) Then distance#=distance#*2
   If cyc=gamPlayer(slot) And charFollowTim(pChar(v))>0 Then distance#=distance#*2
   If v=gamPlayer(slot) And charFollowTim(pChar(cyc))>0 Then distance#=distance#*2
   If pGrappler(v)>0 Then distance#=distance#*2 
   If cyc<>v And distance#<hi# Then value=v : hi#=distance#
  Next
 EndIf
 Return value
End Function

;IN PROXIMITY OF ENEMY?
Function InProximity(cyc,v,range)
 value=0
 If pSeat(cyc)>0 And pSeat(v)>0 Then range=range*2
 If pY#(v)>pY#(cyc)-30 And pY#(v)<pY#(cyc)+30 
  checkX#=pX#(cyc) : checkZ#=pZ#(cyc)
  If pGrappling(cyc)>0 Or pGrappler(cyc)>0 Then checkX#=EntityX(pLimb(cyc,30),1) : checkZ#=EntityZ(pLimb(cyc,30),1)
  If checkX#>pX#(v)-range And checkX#<pX#(v)+range And checkZ#>pZ#(v)-range And checkZ#<pZ#(v)+range Then value=1
 EndIf
 Return value
End Function

;IN PROXIMITY OF LIMB?
Function LimbProximity(limb,x#,z#,range)
 value=0
 If x#>EntityX(limb,1)-range And x#<EntityX(limb,1)+range And z#>EntityZ(limb,1)-range And z#<EntityZ(limb,1)+range
  value=1
 EndIf
 Return value
End Function

;ENEMY IN RANGE?
Function InRange(cyc,v,range)
 value=0
 If InProximity(cyc,v,range*3)
  ;position probe
  ResetDummy(cyc)
  ;find matches
  For depth=1 To range
   If value=0
    span=6+(depth*2)
    MoveEntity dummy,0,0,4
    checkX#=EntityX(dummy) : checkZ#=EntityZ(dummy)
    If checkX#>pX#(v)-span And checkX#<pX#(v)+span And checkZ#>pZ#(v)-span And checkZ#<pZ#(v)+span Then value=depth
   EndIf
  Next
 EndIf
 Return value
End Function

;IN LINE WITH ENTITY?
Function InLine(cyc,entity,range)
 ;get target angle
 ResetDummy(cyc)
 PointEntity dummy,entity
 tA#=CleanAngle#(EntityYaw(dummy))
 ;find match
 value=0
 If SatisfiedAngle(pA#(cyc),tA#,range) Then value=1
 Return value
End Function

;IS PLAYER ISOLATED?
Function Isolated(cyc,range)
 value=1
 For v=1 To no_plays
  If cyc<>v And Friendly(cyc,v)=0 And InProximity(cyc,v,range) And AttackViable(v)<>3 Then value=0
 Next
 Return value
End Function

;FIND THREAT (NATURE OF)
Function FindThreat(cyc) ;1=high, 2=low
 threat=0
 If AttackViable(cyc)=>1 And AttackViable(cyc)=<2
  For v=1 To no_plays
   If cyc<>v And cyc=pFoc(v)
    ;hand-to-hand threats
    range=30
    If pWeapon(v)>0 Then range=range+(weapRange#(weapType(pWeapon(v)))*2)
    If InProximity(cyc,v,range) And pAnim(v)=>30 And pAnim(v)=<49 And pSting(v)=1
     If InLine(v,p(cyc),90)
      If pAnim(v)=31 Or pAnim(v)=35 Or pAnim(v)=41 Then threat=2 Else threat=1
      pFoc(cyc)=v
     EndIf
    EndIf 
    ;gun threats
    ;If InProximity(cyc,v,500) And pAnim(v)=>60 And pAnim(v)=<61 And pFireTim(v)=>5
     ;If pY#(cyc)<pY#(v)-5 Or pY#(cyc)>pY#(v)+5 Then threat=2 Else threat=1
     ;pFoc(cyc)=v 
    ;EndIf  
   EndIf
  Next
 EndIf
 Return threat
End Function

;IDENTIFY FRIENDLY RELATIONSHIP (between players)
Function Friendly(cyc,v)
 friendly=0
 If charAngerTim(pChar(cyc),pChar(v))=0 And charRelation(pChar(cyc),pChar(v))=>0
  If charRelation(pChar(cyc),pChar(v))=1 Then friendly=1
  If charGang(pChar(cyc))>0 And charGang(pChar(cyc))=charGang(pChar(v)) Then friendly=1
  If pChar(cyc)=gamChar(slot) And (charFollowTim(pChar(v))>0 Or charBribeTim(pChar(v))>0) Then friendly=1
  If pChar(v)=gamChar(slot) And (charFollowTim(pChar(cyc))>0 Or charBribeTim(pChar(cyc))>0) Then friendly=1 
 EndIf
 Return friendly
End Function

;IDENTIFY FRIENDLY RELATIONSHIP (between characters)
Function FriendlyChars(char,v)
 friendly=0
 If charAngerTim(char,v)=0 And charRelation(char,v)=>0
  If charRelation(char,v)=1 Then friendly=1
  If charGang(char)>0 And charGang(char)=charGang(v) Then friendly=1
 EndIf
 Return friendly
End Function

;RISK ANGER
Function RiskAnger(cyc,v)
 ;pre-anger promos
 If pChar(cyc)=gamChar(slot) And charPromo(pChar(v),pChar(cyc))=0 
  If GetResponse(cyc,v,0)>0 And charRole(pChar(v))=1 And (pWeapon(cyc)=0 Or promoUsed(1))
   charPromo(pChar(v),pChar(cyc))=5 ;assaulted officer
  EndIf
  If GetResponse(cyc,v,0)>0 And charRole(pChar(v))=0 And charAngerTim(pChar(v),pChar(cyc))=0 And charRelation(pChar(v),pChar(cyc))=>0
   charPromo(pChar(v),pChar(cyc))=14 ;unprovoked attack
   If charGang(pChar(v))>0 And charGang(pChar(v))<>6 And charGang(pChar(v))<>charGang(pChar(cyc)) Then charPromo(pChar(v),pChar(cyc))=40 ;rival gang member
   If charGang(pChar(v))>0 And charGang(pChar(v))<>6 And charGang(pChar(v))=charGang(pChar(cyc)) Then charPromo(pChar(v),pChar(cyc))=41 ;fellow gang member
   If charRelation(pChar(v),pChar(cyc))>0 Then charPromo(pChar(v),pChar(cyc))=80 ;fall out with friend
   If charGang(pChar(v))=6 Then charPromo(pChar(v),pChar(cyc))=43 ;forgiven for attack
  EndIf
  If GetResponse(cyc,v,0)>0 And pSeat(v)>0 And charGang(pChar(v))<>6 Then charPromo(pChar(v),pChar(cyc))=9 ;lost seat
  If GetResponse(cyc,v,0)>0 And pBed(v)>0 And charGang(pChar(v))<>6 Then charPromo(pChar(v),pChar(cyc))=10 ;lost bed
  randy=Rnd(0,20)
  If randy=0 And charRole(pChar(v))=1 And gamWarrant(slot)<9 And charBribeTim(pChar(v))=0 Then gamWarrant(slot)=9 ;assault officer warrantt 
  ;beg for mercy
  If charReputation(pChar(v))<charReputation(pChar(cyc)) And promoUsed(258)=0
   randy=Rnd(0,pHealth(v)*2)
   If randy=0 And pHealth(v)>0 And pHealth(v)<20 Then charPromo(pChar(v),pChar(cyc))=258
   randy=Rnd(0,charHappiness(pChar(v))*2)
   If randy=0 And charHappiness(pChar(v))>0 And charHappiness(pChar(v))<20 Then charPromo(pChar(v),pChar(cyc))=258
  EndIf
  ;response to intervening
  For count=1 To no_plays
   If count<>cyc And count<>v And charPromo(pChar(count),pChar(cyc))=0 And charAttacker(pChar(count))=pChar(v) And charAngerTim(pChar(count),pChar(cyc))=0 And charAngerTim(pChar(cyc),pChar(v))=0 And charAngerTim(pChar(v),pChar(cyc))=0
    If charAngerTim(pChar(count),pChar(v))>0 Or charAngerTim(pChar(v),pChar(count))>0
     If InProximity(count,cyc,30) Or InProximity(count,v,30)
      randy=Rnd(0,6)
      If randy=0 Or (randy=1 And charRelation(pChar(count),pChar(cyc))>0) Then charPromo(pChar(count),pChar(cyc))=78
      If (randy=2 And charRelation(pChar(count),pChar(cyc))=<0) Or (randy=3 And charRelation(pChar(count),pChar(cyc))<0) Then charPromo(pChar(count),pChar(cyc))=79
      charPromoRef(pChar(count))=pChar(v)
     EndIf
    EndIf  
   EndIf
  Next
 EndIf
 ;offer mercy
 If pChar(v)=gamChar(slot) And gamMoney(slot)>0 And charPromo(pChar(cyc),pChar(v))=0 And charReputation(pChar(v))<charReputation(pChar(cyc)) And promoUsed(259)=0
  randy=Rnd(0,pHealth(v)*2)
  If randy=0 And pHealth(v)>0 And pHealth(v)<20 Then charPromo(pChar(cyc),pChar(v))=259
  randy=Rnd(0,charHappiness(pChar(v))*2)
  If randy=0 And charHappiness(pChar(v))>0 And charHappiness(pChar(v))<20 Then charPromo(pChar(cyc),pChar(v))=259
 EndIf 
 ;anger victim 
 If charGang(pChar(v))<>6
  reaction=(charStrength(pChar(v))-30)+(100-charIntelligence(pChar(v)))+(charReputation(pChar(v))-30)
  charAngerTim(pChar(v),pChar(cyc))=Rnd(reaction/2,reaction*4)
  pAgenda(v)=2 : pFollowFoc(v)=cyc
  pSubX#(v)=9999 : pSubZ#(v)=9999 
  randy=Rnd(0,10)
  If randy=0 And charRelation(pChar(v),pChar(cyc))=>0 Then ChangeRelationship(pChar(v),pChar(cyc),-1)
 EndIf
 ;reset witnesses
 charWitness(pChar(cyc))=0
 If charRole(pChar(v))=1 And pHealth(v)>0 Then charWitness(pChar(cyc))=pChar(v)
 ;affect others
 For count=1 To no_plays
  If count<>cyc And count<>v And pChar(count)<>gamChar(slot) And gamBlackout(slot)=0
   ;find witnesses
   If charWitness(pChar(cyc))=0 And (Friendly(count,cyc)=0 Or Friendly(count,v)) And charBribeTim(pChar(count))=0 And InProximity(count,cyc,100) And AttackViable(count)=>1 And AttackViable(count)=<2 And pDazed(count)=0
    If InLine(count,p(cyc),60) Or InLine(count,p(v),60) Then charWitness(pChar(cyc))=pChar(v)
   EndIf
   ;include friends
   If Friendly(count,v) And Friendly(count,cyc)=0 And AttackViable(count)=>1 And AttackViable(count)=<2 And pDazed(count)=0
    If InLine(count,p(cyc),60) Or InLine(count,p(v),60) ;Or InProximity(count,cyc,30) Or InProximity(count,v,30)
     If charAngerTim(pChar(count),pChar(cyc))=0 Then charAngerTim(pChar(count),pChar(cyc))=charAngerTim(pChar(v),pChar(cyc))/2
     If charRole(pChar(cyc))=1 And charRole(pChar(count))=0 Then charAngerTim(pChar(count),pChar(cyc))=charAngerTim(pChar(count),pChar(cyc))/2
     If pFollowFoc(count)<>cyc Then pSubX#(count)=9999 : pSubZ#(count)=9999
     pAgenda(count)=2 : pFollowFoc(count)=cyc 
     If GetResponse(cyc,count,0)>0 And pChar(cyc)=gamChar(slot) And charPromo(pChar(count),pChar(cyc))=0 And charGang(pChar(count))<>6
      If charRelation(pChar(count),pChar(v))=1 Then charPromo(pChar(count),pChar(cyc))=15
      If charGang(pChar(v))>0 And charGang(pChar(v))=charGang(pChar(count)) Then charPromo(pChar(count),pChar(cyc))=39
      charPromoRef(pChar(count))=pChar(v)
     EndIf
     If GetResponse(cyc,count,0)>0 And pChar(v)=gamChar(slot) And charPromo(pChar(count),pChar(v))=0 And charGang(pChar(count))<>6
      charPromo(pChar(count),pChar(v))=77
      charPromoRef(pChar(count))=pChar(cyc)
     EndIf
    EndIf
   EndIf
   ;clock civil war
   If charGang(pChar(count))>0 And charGang(pChar(cyc))=charGang(pChar(count)) And charGang(pChar(v))=charGang(pChar(count)) And AttackViable(count)=>1 And AttackViable(count)=<2 And pDazed(count)=0
    If InLine(count,p(cyc),60) Or InLine(count,p(v),60) ;Or InProximity(count,cyc,30) Or InProximity(count,v,30)
     If charAngerTim(pChar(count),pChar(cyc))=0 Then charAngerTim(pChar(count),pChar(cyc))=charAngerTim(pChar(v),pChar(cyc))/2
     If pFollowFoc(count)<>cyc Then pSubX#(count)=9999 : pSubZ#(count)=9999
     pAgenda(count)=2 : pFollowFoc(count)=cyc 
     If GetResponse(cyc,count,0)>0 And pChar(cyc)=gamChar(slot) And charPromo(pChar(count),pChar(cyc))=0 And charGang(pChar(count))<>6
      charPromo(pChar(count),pChar(cyc))=41
      charPromoRef(pChar(count))=pChar(v)
     EndIf
    EndIf
   EndIf
   ;include guards
   If charRole(pChar(count))=1 And charRole(pChar(cyc))=0 And (Friendly(count,cyc)=0 Or Friendly(count,v)) And charBribeTim(pChar(count))=0 And AttackViable(count)=>1 And AttackViable(count)=<2 And pDazed(count)=0
    If InLine(count,p(cyc),60) Or InLine(count,p(v),60) ;Or InProximity(count,cyc,30) Or InProximity(count,v,30) 
     If charAngerTim(pChar(count),pChar(cyc))<100 Then charAngerTim(pChar(count),pChar(cyc))=100 
     If pFollowFoc(count)<>cyc Then pSubX#(count)=9999 : pSubZ#(count)=9999
     pAgenda(count)=2 : pFollowFoc(count)=cyc
     If pChar(cyc)=gamChar(slot) And charPromo(pChar(count),pChar(cyc))=0 And charBribeTim(pChar(count))=0
      If GetResponse(cyc,count,0)>0
       If charRole(pChar(v))=0 Then charPromo(pChar(count),pChar(cyc))=4 
       If charRole(pChar(v))=1 Then charPromo(pChar(count),pChar(cyc))=5
       If pWeapon(cyc)>0 And promoUsed(72)=0 Then charPromo(pChar(count),pChar(cyc))=72 : charPromoRef(pChar(count))=pChar(v)
      EndIf
      randy=Rnd(0,50)
      If randy=<1 And charRole(pChar(v))=1 And gamWarrant(slot)<9 Then gamWarrant(slot)=9 ;assault officer warrant
      If randy=2 And charRole(pChar(v))=0 And gamWarrant(slot)<8 Then gamWarrant(slot)=8 ;assault warrant
      If pWeapon(cyc)>0 And pAnim(cyc)<>23 And gamMission(slot)<>11 And gamMission(slot)<>12 
       randy=Rnd(0,50)
       If (weapType(pWeapon(cyc))=>7 And weapType(pWeapon(cyc))=<9) Or weapType(pWeapon(cyc))=23 Then randy=Rnd(0,25)
       If randy=<1 And gamWarrant(slot)<10 Then gamWarrant(slot)=10 : gamItem(slot)=pWeapon(cyc) ;assault w/ weapon
       If randy=2 And gamWarrant(slot)<4 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(cyc) ;carrying weapon
      EndIf
      randy=Rnd(0,5)
      If randy=0 And pAnim(cyc)=23 And pWeapon(cyc)>0 And gamMission(slot)<>11 And gamMission(slot)<>12 
       If gamWarrant(slot)<7 Then gamWarrant(slot)=7 : gamItem(slot)=pWeapon(cyc) ;caught stealing 
      EndIf
     EndIf
    EndIf
   EndIf
  EndIf
  ;friend asks for help
  If count<>cyc And count<>v And pChar(count)=gamChar(slot) And Friendly(count,v) And charAngerTim(pChar(count),pChar(cyc))=0 And charAngerTim(pChar(cyc),pChar(count))=0 And InProximity(count,v,50) And gamBlackout(slot)=0
   randy=Rnd(0,5)
   If randy=0 And charPromo(pChar(v),pChar(count))=0 And promoUsed(249)=0 Then charPromo(pChar(v),pChar(count))=249 : charPromoRef(pChar(v))=pChar(cyc)
  EndIf
  ;calm down after intervention
  If charRole(pChar(cyc))=1 And charRole(pChar(v))=0
   charAngerTim(pChar(v),count)=charAngerTim(pChar(v),count)/2
  EndIf
 Next
End Function

;RISK RESPONSE (v to cyc)
Function GetResponse(cyc,v,chance)
 ;establish likelihood
 If chance=0
  chance=(charReputation(pChar(cyc))/5)-(charReputation(pChar(v))/5)
  If chance<1 Or charRelation(pChar(v),pChar(cyc))=-1 Or charRole(pChar(v))=1 Then chance=1
 EndIf
 ;risk response
 response=0
 randy=Rnd(0,chance)
 If randy=0 Then response=1
 Return response
End Function

;TRADING RISK
Function TradingRisk(cyc,weapon)
 For v=1 To no_plays
  If charRole(pChar(v))=1 And v<>pFoc(cyc) And Friendly(v,cyc)=0 And charBribeTim(pChar(v))=0 And gamBlackout(slot)=0 And AttackViable(v)=>1 And AttackViable(v)=<2 And pDazed(v)=0 And InProximity(v,cyc,50)
   If InLine(v,p(cyc),60)
    randy=Rnd(0,5)
    If randy=0 And pChar(cyc)=gamChar(slot) And gamMission(slot)<>11 And gamMission(slot)<>12 
     If gamWarrant(slot)<6 Then gamWarrant(slot)=6 : gamItem(slot)=weapon
    EndIf
    pAgenda(v)=4 : pWeapFoc(v)=weapon
    pSubX#(v)=9999 : pSubZ#(v)=9999
   EndIf
  EndIf
 Next
End Function

;ASSESS RELATIONSHIPS
Function AssessRelationships(cyc)
 If FocViable(cyc)
  ;look at nearest by default
  pFoc(cyc)=NearestEnemy(cyc)
  ;consider relationships
  For v=1 To no_plays
   ;guarantee racial friction
   If pChar(cyc)<>gamChar(slot)
    If charGang(pChar(cyc))=>1 And charGang(pChar(cyc))=<3 And charGang(pChar(cyc))<>charGang(pChar(v)) And GetRace(pChar(cyc))<>GetRace(pChar(v)) And Friendly(cyc,v)=0
     charRelation(pChar(cyc),pChar(v))=-1
    EndIf
   EndIf
   ;calm down
   If gamPromo=0 
    charAngerTim(pChar(cyc),pChar(v))=charAngerTim(pChar(cyc),pChar(v))-1
    If AttackViable(cyc)=3 Or AttackViable(v)=3 Then charAngerTim(pChar(cyc),pChar(v))=charAngerTim(pChar(cyc),pChar(v))-1
    If charRole(pChar(cyc))=0 And charRole(pChar(v))=0 And charReputation(pChar(cyc))<charReputation(pChar(v)) Then charAngerTim(pChar(cyc),pChar(v))=charAngerTim(pChar(cyc),pChar(v))-1
    If charRole(pChar(cyc))=0 And charRole(pChar(v))=1 And InProximity(cyc,v,100) Then charAngerTim(pChar(cyc),pChar(v))=charAngerTim(pChar(cyc),pChar(v))-1
   EndIf
   If charAngerTim(pChar(cyc),pChar(v))<0 Then charAngerTim(pChar(cyc),pChar(v))=0
   ;spontaneous anger
   If charAngerTim(pChar(cyc),pChar(v))=0
    randy=Rnd(0,10000)
    If randy=0 And charRelation(pChar(cyc),pChar(v))=0 Then charAngerTim(pChar(cyc),pChar(v))=10
    If randy=<5 And charRelation(pChar(cyc),pChar(v))=-1 Then charAngerTim(pChar(cyc),pChar(v))=10
   EndIf
   ;law enforcement
   If charRole(pChar(cyc))=1 And charRole(pChar(v))=0 And Friendly(cyc,v)=0 And charBribeTim(pChar(cyc))=0 And gamBlackout(slot)=0 And AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And pDazed(cyc)=0
    ;confiscate weapons
    If pWeapon(v)>0 And weapHabitat(weapType(pWeapon(v)))<99 And weapHabitat(weapType(pWeapon(v)))<>gamLocation(slot) And InProximity(cyc,v,weapSize#(weapType(pWeapon(v)))*5)
     If InLine(cyc,p(v),60)
      randy=Rnd(0,100)
      If randy=0 Or pChar(v)=gamChar(slot)
       If pAgenda(cyc)<>4 Then pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
       pAgenda(cyc)=4 : pWeapFoc(cyc)=pWeapon(v)
       If charAngerTim(pChar(cyc),pChar(v))<10 Then charAngerTim(pChar(cyc),pChar(v))=10
      EndIf
      randy=Rnd(0,1000)
      If (weapType(pWeapon(v))=>7 And weapType(pWeapon(v))=<9) Or weapType(pWeapon(v))=23 Then randy=Rnd(0,500)
      If randy=0 Or (randy=1 And weapHabitat(weapType(pWeapon(v)))=0)
       If pChar(v)=gamChar(slot) And gamPromo=0 And gamMission(slot)<>11 And gamMission(slot)<>12 
        If gamWarrant(slot)<4 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
       EndIf
      EndIf
     EndIf
    EndIf
    ;clock drug abuse
    If pAnim(v)=>93 And pAnim(v)=<95 And weapHabitat(weapType(pWeapon(v)))<>gamLocation(slot) And InProximity(cyc,v,50)
     If InLine(cyc,p(v),60)
      randy=Rnd(0,100)
      If randy=0 Or pChar(v)=gamChar(slot)
       If pAgenda(cyc)<>4 Then pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
       pAgenda(cyc)=4 : pWeapFoc(cyc)=pWeapon(v)
       If charAngerTim(pChar(cyc),pChar(v))<10 Then charAngerTim(pChar(cyc),pChar(v))=10
      EndIf
      randy=Rnd(0,500)
      If randy=<1 And pChar(v)=gamChar(slot) And gamWarrant(slot)<5 And gamPromo=0 Then gamWarrant(slot)=5
     EndIf
    EndIf
    ;clock gang membership
    If pChar(v)=gamChar(slot) And charGang(pChar(v))>0 And InProximity(cyc,v,30)
     If InLine(cyc,p(v),60)
      randy=Rnd(0,5000)
      If randy=0 And gamWarrant(slot)<2 And gamPromo=0 Then gamWarrant(slot)=2
     EndIf
    EndIf
    ;out of block during lock down
    If LockDown() And pChar(v)=gamChar(slot) And GetBlock(gamLocation(slot))<>charBlock(pChar(v))
     If InLine(cyc,p(v),60)
      If pAgenda(cyc)<>2 Then pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
      pAgenda(cyc)=2 : pFollowFoc(cyc)=v
      randy=Rnd(0,100)
      If randy=0 And charAngerTim(pChar(cyc),pChar(v))<10 Then charAngerTim(pChar(cyc),pChar(v))=10
      randy=Rnd(0,5000)
      If randy=0 And InProximity(cyc,v,100) And (gamHours(slot)>22 Or gamHours(slot)<7) And gamWarrant(slot)<1 And gamPromo=0 Then gamWarrant(slot)=1
      If randy=<50 And InProximity(cyc,v,100) And gamEscape(slot)=1 And gamWarrant(slot)<3 And gamPromo=0 Then gamWarrant(slot)=3
     EndIf
    EndIf
    ;out of cell during lock down
    If LockDown() And pChar(v)=gamChar(slot) And GetBlock(gamLocation(slot))=charBlock(pChar(v)) And InsideCell(pX#(v),pY#(v),pZ#(v))<>charCell(pChar(v)) And pAnim(v)<>12 And pAnim(v)<>13
     If InLine(cyc,p(v),60)
      If pAgenda(cyc)<>2 Then pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
      pAgenda(cyc)=2 : pFollowFoc(cyc)=v
      randy=Rnd(0,100)
      If randy=0 And charAngerTim(pChar(cyc),pChar(v))<10 Then charAngerTim(pChar(cyc),pChar(v))=10
      randy=Rnd(0,5000)
      If randy=0 And InProximity(cyc,v,100) And (gamHours(slot)>22 Or gamHours(slot)<7) And gamWarrant(slot)<1 And gamPromo=0 Then gamWarrant(slot)=1
     EndIf
    EndIf
   EndIf
   ;wave to friends
   cellConflict=0 : cell=InsideCell(pX#(v),pY#(v),pZ#(v))
   If cell>0 And CellVisible(pX#(cyc),pY#(cyc),pZ#(cyc),cell)=0 Then cellConflict=1
   If cellConflict=0 And cyc<>v And InProximity(cyc,v,100) And pInteract(cyc,v)=0 And Friendly(cyc,v) And Friendly(v,cyc) And pAnim(cyc)<20 And pAnim(v)<20 And pDazed(cyc)=0 And pDazed(v)=0 And v<>promoActor(1) And v<>promoActor(2)
    If (charFollowTim(pChar(cyc))=0 Or v<>gamPlayer(slot)) And (charFollowTim(pChar(v))=0 Or cyc<>gamPlayer(slot)) 
     If InLine(cyc,p(v),60) And InLine(v,p(cyc),60)
      pFoc(cyc)=v : ChangeAnim(cyc,91)
      pFoc(v)=cyc : ChangeAnim(v,91)
      pInteract(cyc,v)=1 : pInteract(v,cyc)=1
     EndIf
    EndIf
   EndIf
   ;trigger promos
   If charPromo(pChar(cyc),pChar(v))=>202 And charPromo(pChar(cyc),pChar(v))=<204
    If GetBlock(gamLocation(slot))<>charBlock(pChar(cyc)) Or InsideCell(pX#(cyc),pY#(cyc),pZ#(cyc))<>charCell(pChar(cyc)) Or InsideCell(pX#(v),pY#(v),pZ#(v))<>charCell(pChar(v))
     cellConflict=1
    EndIf
   EndIf
   If gotim>50 And gamPromo=0 And promoTim=0 And cellConflict=0 And cyc<>v And pHealth(cyc)>0 And pHealth(v)>0 And pPhone(cyc)=0 And pPhone(v)=0 And pAnim(v)<>90
    oldPromo=charPromo(pChar(cyc),pChar(v))
    RiskPromo(cyc,v)
    If charPromo(pChar(cyc),pChar(v))>0
     If InProximity(cyc,v,50) Or (InProximity(cyc,v,100) And oldPromo<>charPromo(pChar(cyc),pChar(v)))
      TriggerPromo(cyc,v,charPromo(pChar(cyc),pChar(v)))
      charPromo(pChar(cyc),pChar(v))=0
     EndIf
    EndIf
   EndIf
  Next
 EndIf
 ;pursue warrant
 If charRole(pChar(cyc))=1 And gamWarrant(slot)>0 And charBribeTim(pChar(cyc))=0  
  If pAgenda(cyc)<>2 Then pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
  pAgenda(cyc)=2 : pFollowFoc(cyc)=gamPlayer(slot)
  If charAngerTim(pChar(cyc),gamChar(slot))<10 Then charAngerTim(pChar(cyc),gamChar(slot))=10
 EndIf
 ;promo override
 If gamPromo>0
  If cyc=promoActor(1) And promoActor(2)>0 Then pFoc(cyc)=promoActor(2)
  If cyc=promoActor(2) And promoActor(1)>0 Then pFoc(cyc)=promoActor(1)
 EndIf
End Function