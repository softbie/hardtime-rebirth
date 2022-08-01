;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: ANIMATIONS ---------------------------
;//////////////////////////////////////////////////////////////////////////////

;-----------------------------------------------------------------
;/////////////////// LOAD MOVE SEQUENCES /////////////////////////
;-----------------------------------------------------------------
Function LoadMoveSequences(cyc)
 ;application
 pSeq(cyc,200)=ExtractAnimSeq(p(cyc),1650,1720,pSeq(cyc,603)) ;standing grapple lunge
 pSeq(cyc,201)=ExtractAnimSeq(p(cyc),1730,1800,pSeq(cyc,603)) ;ground grapple lunge
 pSeq(cyc,202)=ExtractAnimSeq(p(cyc),60,100,pSeq(cyc,610)) ;apply headlock [execute]
 pSeq(cyc,203)=ExtractAnimSeq(p(cyc),60,100,pSeq(cyc,611)) ;apply headlock [receive]
 pSeq(cyc,204)=ExtractAnimSeq(p(cyc),320,400,pSeq(cyc,610)) ;pick up from floor [execute]
 pSeq(cyc,205)=ExtractAnimSeq(p(cyc),320,400,pSeq(cyc,611)) ;pick up from floor [receive] 
 pSeq(cyc,206)=ExtractAnimSeq(p(cyc),110,150,pSeq(cyc,610)) ;hold headlock [execute]
 pSeq(cyc,207)=ExtractAnimSeq(p(cyc),110,150,pSeq(cyc,611)) ;hold headlock [receive]
 pSeq(cyc,208)=ExtractAnimSeq(p(cyc),250,310,pSeq(cyc,610)) ;headlock movement [execute]
 pSeq(cyc,209)=ExtractAnimSeq(p(cyc),250,310,pSeq(cyc,611)) ;headlock movement [receive]
 ;actions
 pSeq(cyc,210)=ExtractAnimSeq(p(cyc),160,240,pSeq(cyc,610)) ;release headlock [execute]
 pSeq(cyc,211)=ExtractAnimSeq(p(cyc),160,240,pSeq(cyc,611)) ;release headlock [receive]
 pSeq(cyc,212)=ExtractAnimSeq(p(cyc),410,450,pSeq(cyc,610)) ;headlock punch [execute]
 pSeq(cyc,213)=ExtractAnimSeq(p(cyc),410,450,pSeq(cyc,611)) ;headlock punch [receive]
 pSeq(cyc,214)=ExtractAnimSeq(p(cyc),460,500,pSeq(cyc,610)) ;knee to face [execute]
 pSeq(cyc,215)=ExtractAnimSeq(p(cyc),460,500,pSeq(cyc,611)) ;knee to face [receive]
 pSeq(cyc,216)=ExtractAnimSeq(p(cyc),510,650,pSeq(cyc,610)) ;headlock takedown [execute]
 pSeq(cyc,217)=ExtractAnimSeq(p(cyc),510,650,pSeq(cyc,611)) ;headlock takedown [receive]
 pSeq(cyc,218)=ExtractAnimSeq(p(cyc),660,840,pSeq(cyc,610)) ;bodyslam [execute]
 pSeq(cyc,219)=ExtractAnimSeq(p(cyc),660,840,pSeq(cyc,611)) ;bodyslam [receive]
 pSeq(cyc,220)=ExtractAnimSeq(p(cyc),850,1000,pSeq(cyc,610)) ;chokeslam [execute]
 pSeq(cyc,221)=ExtractAnimSeq(p(cyc),850,1000,pSeq(cyc,611)) ;chokeslam [receive]
 pSeq(cyc,222)=ExtractAnimSeq(p(cyc),1010,1185,pSeq(cyc,610)) ;bulldog [execute]
 pSeq(cyc,223)=ExtractAnimSeq(p(cyc),1010,1185,pSeq(cyc,611)) ;bulldog [receive] 
 pSeq(cyc,224)=ExtractAnimSeq(p(cyc),1195,1350,pSeq(cyc,610)) ;push off [execute]
 pSeq(cyc,225)=ExtractAnimSeq(p(cyc),1195,1350,pSeq(cyc,611)) ;push off [receive] 
 pSeq(cyc,226)=ExtractAnimSeq(p(cyc),1360,1520,pSeq(cyc,610)) ;kick throw [execute]
 pSeq(cyc,227)=ExtractAnimSeq(p(cyc),1360,1520,pSeq(cyc,611)) ;kick throw [receive] 
End Function

;-----------------------------------------------------------------
;///////////////////// MOVE ANIMATIONS ///////////////////////////
;-----------------------------------------------------------------
Function MoveAnims(cyc)
 ;------------- HOLDING -------------
 ;standing grapple lunge
 If pAnim(cyc)=200
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,200),5
  If pAnimTim(cyc)=6 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.2,0.5))
  If pAnimTim(cyc)=<15
   FaceEntity(cyc,p(pFoc(cyc)),10)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.5
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>8 And pAnimTim(cyc)=<13 And pScar(cyc,4)=<4 And pGrappling(cyc)=0
   For v=1 To no_plays
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,20) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+5 And AttackViable(v)=>1 And AttackViable(v)=<2 And pGrappling(cyc)=0 And pGrappling(v)=0 And pGrappler(v)=0 And pSeat(v)=0 And pBed(v)=0
     If InRange(cyc,v,6)
      charAttacker(pChar(v))=pChar(cyc)
      ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
      If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      ChangeAnim(cyc,203) : ChangeAnim(v,202)
      pGrappling(cyc)=v : pGrappler(v)=cyc 
      FixGrapple(cyc,v)
      pOldMoveX#(v)=EntityX(pLimb(cyc,30),1)
      pOldMoveZ#(v)=EntityZ(pLimb(cyc,30),1)
      RiskAnger(cyc,v)
      GainStrength(cyc,50)
      DamageRep(cyc,v,0)
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>26 Then ChangeAnim(cyc,0)
 EndIf
 ;ground grapple lunge
 If pAnim(cyc)=201
  If pAnimTim(cyc)=0 Then Animate p(cyc),3,3.0,pSeq(cyc,201),5
  If pAnimTim(cyc)=6 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.2,0.5))
  If pAnimTim(cyc)=<15
   FaceEntity(cyc,p(pFoc(cyc)),10)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   MoveEntity pPivot(cyc),0,0,0.25
   pStepTim(cyc)=pStepTim(cyc)+Rnd(0,1)
  EndIf
  If pAnimTim(cyc)=>10 And pAnimTim(cyc)=<13 And pScar(cyc,4)=<4 And pGrappling(cyc)=0
   For v=1 To no_plays
    If cyc<>v And (Friendly(cyc,v)=0 Or v=pFoc(cyc)) And InProximity(cyc,v,30) And pY#(cyc)>pY#(v)-30 And pY#(cyc)<pY#(v)+5 And AttackViable(v)=3 And pHealth(v)>0 And pGrappling(cyc)=0 And pGrappler(v)=0 And pSeat(v)=0 And pBed(v)=0
     If InRange(cyc,v,8)
      charAttacker(pChar(v))=pChar(cyc)
      ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
      If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      ChangeAnim(cyc,204) : ChangeAnim(v,202)
      randy=Rnd(0,(150-charAgility(pChar(v)))*3)
      If randy=<25
       If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then ChangeAnim(cyc,218)
      EndIf
      pGrappling(cyc)=v : pGrappler(v)=cyc
      FixGrapple(cyc,v)
      pOldMoveX#(v)=EntityX(pLimb(cyc,30),1)
      pOldMoveZ#(v)=EntityZ(pLimb(cyc,30),1)
      RiskAnger(cyc,v)
      If pAnim(cyc)<>218
       DamageRep(cyc,v,0)
       GainStrength(cyc,50)
      EndIf
     EndIf
    EndIf
   Next
  EndIf
  If pAnimTim(cyc)>26 Then ChangeAnim(cyc,0)
 EndIf
 ;move victim
 If pAnim(cyc)=202
  v=pGrappler(cyc)
  If pAnim(v)<200 
   ChangeAnim(cyc,71) : SharpTransition(cyc,71,0)
   pGrappling(v)=0 : pGrappler(cyc)=0 
  EndIf
 EndIf
 ;apply headlock
 If pAnim(cyc)=203
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,2.5,pSeq(cyc,202),0
   Animate p(v),3,2.5,pSeq(v,203),0
  EndIf
  If pAnimTim(cyc)=4 Or pAnimTim(cyc)=12 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=8 Or pAnimTim(cyc)=16 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>16
   ChangeAnim(cyc,205)
   randy=Rnd(0,(150-charStrength(pChar(v)))*3)
   If randy=<25
    If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then ChangeAnim(cyc,217)
   EndIf
  EndIf
  DropWeapon(v,5)
 EndIf
 ;pick up from floor
 If pAnim(cyc)=204
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,2.0,pSeq(cyc,204),0
   Animate p(v),3,2.0,pSeq(v,205),0
  EndIf
  If pAnimTim(cyc)=5 Or pAnimTim(cyc)=15 Or pAnimTim(cyc)=25 Or pAnimTim(cyc)=35 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=10 Or pAnimTim(cyc)=20 Or pAnimTim(cyc)=30 Or pAnimTim(cyc)=40 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>40
   ChangeAnim(cyc,205) : pHP(v)=Rnd(-2,5)
   randy=Rnd(0,(150-charStrength(pChar(v)))*3)
   If randy=<25
    If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then ChangeAnim(cyc,217)
   EndIf
  EndIf
  DropWeapon(v,5)
 EndIf
 ;hold headlock
 If pAnim(cyc)=205
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),1,0.5,pSeq(cyc,206),5
   Animate p(v),1,0.5,pSeq(v,207),5
  EndIf
  FixGrapple(cyc,v)
  If DirPressed(cyc) Then ChangeAnim(cyc,206)
  FindMoveCommands(cyc) 
  DropWeapon(v,0)
 EndIf
 ;headlock movement
 If pAnim(cyc)=206
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),1,1.5,pSeq(cyc,208),5
   Animate p(v),1,1.5,pSeq(v,209),5
  EndIf
  If cLeft(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)+1)
  If cRight(cyc) Then pA#(cyc)=CleanAngle#(pA#(cyc)-1)
  If VerticalPressed(cyc)
   RotateEntity pPivot(cyc),0,pA#(cyc),0
   If cUp(cyc) Then MoveEntity pPivot(cyc),0,0,-0.3
   If cDown(cyc) Then MoveEntity pPivot(cyc),0,0,0.15
  EndIf
  FixGrapple(cyc,v)
  If pAnimTim(cyc)>5 And DirPressed(cyc)=0 Then ChangeAnim(cyc,205)
  FindMoveCommands(cyc)
  pStepTim(cyc)=pStepTim(cyc)+1
 EndIf
 ;------------- MOVES -------------
 ;release headlock
 If pAnim(cyc)=210
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.0,pSeq(cyc,210),0
   Animate p(v),3,3.0,pSeq(v,211),0
  EndIf
  If pAnimTim(cyc)=10 Or pAnimTim(cyc)=20 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=5 Or pAnimTim(cyc)=15 Then pStepTim(cyc)=99
  If pAnimTim(cyc)>26
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   ChangeAnim(cyc,0) : SharpTransition(cyc,1,0)
   ChangeAnim(v,0) : SharpTransition(v,1,180)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;headlock punch
 If pAnim(cyc)=211
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),1,2.5,pSeq(cyc,212),0
   Animate p(v),1,2.5,pSeq(v,213),0
  EndIf
  If pAnimTim(cyc)=7 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.1,0.3))
  If pAnimTim(cyc)=12
   ProduceSound(p(cyc),sImpact(Rnd(1,2)),22050,0)
   If pHealth(v)>0 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   CreateSpurt(EntityX(pLimb(cyc,6),1),EntityY(pLimb(cyc,6),1),EntityZ(pLimb(cyc,6),1),1,5,99)
   ScarLimb(v,1,10) : pDT(v)=(150-pHealth(v))*2
   pHealth(v)=pHealth(v)-1 : pHP(v)=pHP(v)-1
   WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,50) : DamageRep(cyc,v,0)
  EndIf
  If pAnimTim(cyc)=>16
   pAnimTim(cyc)=0
   If (cAttack(cyc)=0 Or DirPressed(cyc)=0) And pHP(v)>0 Then ChangeAnim(cyc,205)
   If pHP(v)=<0 Then ChangeAnim(cyc,210)
   randy=Rnd(0,(150-charStrength(pChar(v)))*3)
   If pAnim(cyc)=211 And randy=<10
    If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then ChangeAnim(cyc,217)
   EndIf
  EndIf
 EndIf
 ;knee to face
 If pAnim(cyc)=212
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),1,2.25,pSeq(cyc,214),0
   Animate p(v),1,2.25,pSeq(v,215),0
  EndIf
  If pAnimTim(cyc)=4 Then ProduceSound(p(cyc),sSwing,22050,Rnd(0.2,0.5))
  If pAnimTim(cyc)=9
   randy=Rnd(1,2)
   If randy=1 Then ProduceSound(p(cyc),sImpact(3),22050,0)
   If randy=2 Then ProduceSound(p(cyc),sImpact(6),22050,0)
   If pHealth(v)>0 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   CreateSpurt(EntityX(pLimb(cyc,35),1),EntityY(pLimb(cyc,35),1),EntityZ(pLimb(cyc,35),1),1,5,99)
   ScarLimb(v,1,10) : pDT(v)=(150-pHealth(v))*2
   pHealth(v)=pHealth(v)-1 : pHP(v)=pHP(v)-1
   WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,50) : DamageRep(cyc,v,0)
  EndIf
  If pAnimTim(cyc)=>18
   pAnimTim(cyc)=0
   If (cAttack(cyc)=0 Or DirPressed(cyc)) And pHP(v)>0 Then ChangeAnim(cyc,205)
   If pHP(v)=<0 Then ChangeAnim(cyc,210)
   randy=Rnd(0,(150-charStrength(pChar(v)))*3)
   If pAnim(cyc)=212 And randy=<10
    If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then ChangeAnim(cyc,217)
   EndIf
  EndIf
 EndIf
 ;headlock takedown
 If pAnim(cyc)=213
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.0,pSeq(cyc,216),0
   Animate p(v),3,3.0,pSeq(v,217),0
  EndIf
  If pAnimTim(cyc)=1 Or pAnimTim(cyc)=30 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=40 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=10 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=20
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sImpact(6),22050,0) : ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(v)=pHealth(v)-GetPower(cyc)*3 : pDT(v)=(150-pHealth(v))*2 
   pHP(v)=0 : pHP(cyc)=pHP(cyc)-Rnd(0,1)
   RiskInjury(v,100) : ScarArea(v,0,0,0,10)
   FindSmashes(cyc) : FindSmashes(v)
   WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,10) : DamageRep(cyc,v,1)
  EndIf
  If pAnimTim(cyc)>46
   ChangeAnim(cyc,0) : SharpTransition(cyc,1,180)
   ChangeAnim(v,81) : SharpTransition(v,81,180)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;bodyslam
 If pAnim(cyc)=214
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.0,pSeq(cyc,218),0
   Animate p(v),3,3.0,pSeq(v,219),0
  EndIf
  If pAnimTim(cyc)=10 Or pAnimTim(cyc)=20 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=5 Or pAnimTim(cyc)=15 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=25 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=46
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sImpact(6),22050,0) : ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(v)=pHealth(v)-GetPower(cyc)*3 : pDT(v)=(150-pHealth(v))*2
   pHP(v)=0 : pHP(cyc)=pHP(cyc)-Rnd(0,1)
   RiskInjury(v,100) : ScarArea(v,0,0,0,10)
   FindSmashes(v) : WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,10) : DamageRep(cyc,v,1)
  EndIf
  If pAnimTim(cyc)>60
   ChangeAnim(cyc,0) : SharpTransition(cyc,1,0.1)
   ChangeAnim(v,81) : SharpTransition(v,81,0.1)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;chokeslam
 If pAnim(cyc)=215
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.75,pSeq(cyc,220),0
   Animate p(v),3,3.75,pSeq(v,221),0
  EndIf
  If pAnimTim(cyc)=6 Or pAnimTim(cyc)=12 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=3 Or pAnimTim(cyc)=9 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=12 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=26
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sImpact(3),22050,0) : ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(v)=pHealth(v)-GetPower(cyc)*3 : pDT(v)=(150-pHealth(v))*2
   pHP(v)=0 : pHP(cyc)=pHP(cyc)-Rnd(0,1)
   RiskInjury(v,100) : ScarArea(v,0,0,0,10)
   FindSmashes(v) : WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,10) : DamageRep(cyc,v,1)
  EndIf
  If pAnimTim(cyc)>40
   ChangeAnim(cyc,0) : SharpTransition(cyc,1,90)
   ChangeAnim(v,81) : SharpTransition(v,81,270)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;bulldog
 If pAnim(cyc)=216
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.0,pSeq(cyc,222),0
   Animate p(v),3,3.0,pSeq(v,223),0
  EndIf
  If pAnimTim(cyc)=7 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=48 Or pAnimTim(cyc)=58 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=13 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=26
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sImpact(6),22050,0) : ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(v)=pHealth(v)-GetPower(cyc)*3 : pDT(v)=(150-pHealth(v))*2
   pHP(v)=0 : pHP(cyc)=pHP(cyc)-Rnd(0,1)
   RiskInjury(v,100) : ScarArea(v,0,0,0,10)
   FindSmashes(cyc) : FindSmashes(v)
   WeaponImpact(cyc,v,0) : DropWeapon(cyc,5)
   RiskAnger(cyc,v) : GainStrength(cyc,10) : DamageRep(cyc,v,1)
  EndIf
  If pAnimTim(cyc)>58
   ChangeAnim(cyc,0) : SharpTransition(cyc,1,0)
   ChangeAnim(v,84) : SharpTransition(v,84,0)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;push off
 If pAnim(cyc)=217
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.5,pSeq(cyc,224),0
   Animate p(v),3,3.5,pSeq(v,225),0
  EndIf
  If pAnimTim(cyc)=5 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0) : pStepTim(cyc)=99
  If pAnimTim(cyc)=14
   ProduceSound(p(cyc),sSwing,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
  EndIf
  If pAnimTim(cyc)=30
   charAttacker(pChar(cyc))=pChar(v)
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(cyc)=pHealth(cyc)-GetPower(v)*2 : pDT(cyc)=(100-pHealth(cyc))*2
   pHP(cyc)=0 : pHP(v)=pHP(v)-Rnd(0,1)
   RiskInjury(cyc,100) : ScarArea(cyc,0,0,0,10)
   FindSmashes(cyc) : DropWeapon(cyc,0)
   RiskAnger(v,cyc) : GainStrength(v,10) : DamageRep(v,cyc,1)
  EndIf
  If pAnimTim(cyc)>44
   ChangeAnim(cyc,84) : SharpTransition(cyc,84,0)
   ChangeAnim(v,0) : SharpTransition(v,1,180)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
 ;ground throw
 If pAnim(cyc)=218
  v=pGrappling(cyc)
  If pAnimTim(cyc)=0
   Animate p(cyc),3,3.0,pSeq(cyc,226),0
   Animate p(v),3,3.0,pSeq(v,227),0
  EndIf
  If pAnimTim(cyc)=6 Or pAnimTim(cyc)=38 Then ProduceSound(p(cyc),sShuffle(Rnd(1,3)),22050,0)
  If pAnimTim(cyc)=45 Or pAnimTim(cyc)=53 Then pStepTim(cyc)=99
  If pAnimTim(cyc)=6 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
  If pAnimTim(cyc)=16 Then ProduceSound(p(cyc),sSwing,22050,0)
  If pAnimTim(cyc)=31
   charAttacker(pChar(cyc))=pChar(v)
   ProduceSound(p(cyc),sFall,22050,0) : ProduceSound(p(cyc),sThud,22050,0)
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0) 
   pHealth(cyc)=pHealth(cyc)-GetPower(v)*3 : pDT(cyc)=(150-pHealth(cyc))*2
   pHP(cyc)=0 : pHP(v)=Rnd(1,10)
   RiskInjury(cyc,100) : ScarArea(cyc,0,0,0,10)
   FindSmashes(cyc) : DropWeapon(cyc,0)
   RiskAnger(v,cyc) : GainStrength(v,10) : DamageRep(v,cyc,1)
  EndIf
  If pAnimTim(cyc)>53
   ChangeAnim(cyc,81) : SharpTransition(cyc,81,0)
   ChangeAnim(v,0) : SharpTransition(v,1,0)
   pGrappling(cyc)=0 : pGrappler(v)=0
  EndIf
 EndIf
End Function

;--------------------------------------------------------------
;////////////////// RELATED FUNCTIONS /////////////////////////
;--------------------------------------------------------------

;FIX GRAPPLE
Function FixGrapple(cyc,v)
 ;copy positions
 pX#(v)=pX#(cyc) : pZ#(v)=pZ#(cyc)
 pOldX#(v)=pX#(v) : pOldZ#(v)=pZ#(v)
 ResetEntity pPivot(v)
 PositionEntity pPivot(v),EntityX(pPivot(cyc)),EntityY(pPivot(cyc)),EntityZ(pPivot(cyc))
 EntityType pPivot(v),1,0
 EntityRadius pPivot(v),8,18 
 ;same orientation
 pA#(v)=pA#(cyc) : pHurtA#(v)=pA#(cyc) 
 RotateEntity pPivot(v),0,pA#(v),0
 ;discomfort
 randy=Rnd(0,50)
 If randy=0
  ProduceSound(p(v),sShuffle(Rnd(1,3)),22050,0)
  ProduceSound(p(v),sPain(Rnd(1,8)),22050,Rnd(0.1,0.5))
 EndIf
End Function

;FIND MOVE COMMANDS
Function FindMoveCommands(cyc)
 ;CPU choices
 move=0 : v=pGrappling(cyc)
 If pControl(cyc)=0 Or charBreakdown(pChar(cyc))>0
  randy=Rnd(0,100)
  If randy=<10 Then move=Rnd(210,216)
  If Friendly(cyc,v) And charBreakdown(pChar(cyc))=0 Then move=210
 EndIf
 ;human input
 If pControl(cyc)>0
  If cThrow(cyc)=1 Then move=210 ;release
  If cAttack(cyc)=1 And DirPressed(cyc) Then move=211 ;punch
  If cAttack(cyc)=1 And DirPressed(cyc)=0 Then move=212 ;knee
  If cDefend(cyc)=1 And DirPressed(cyc) Then move=214 ;bodyslam
  If cDefend(cyc)=1 And DirPressed(cyc)=0 Then move=215 ;chokeslam
  If cPickUp(cyc)=1 And DirPressed(cyc) Then move=216 ;bulldog
  If cPickUp(cyc)=1 And DirPressed(cyc)=0 Then move=213 ;headlock takedown
 EndIf
 ;find counters
 randy=Rnd(0,(150-charStrength(pChar(v)))*3)
 If randy=<1 Or (randy=<50 And move>0)
  If DirPressed(v) Or ActionPressed(v) Or pControl(v)=0 Then move=217
 EndIf
 ;trigger arrests
 If charRole(pChar(cyc))=1 And pChar(v)=gamChar(slot) And gamWarrant(slot)>0 And gamPromo=0
  randy=Rnd(0,4)
  If randy=0 And gamMoney(slot)>100
   TriggerPromo(cyc,v,52)
  Else
   TriggerPromo(cyc,v,100+gamWarrant(slot))
  EndIf
 EndIf
 ;execute move
 If cyc<>promoActor(1) And cyc<>promoActor(2) And v<>promoActor(1) And v<>promoActor(2)
  If move>0 Then ChangeAnim(cyc,move)
 EndIf
End Function