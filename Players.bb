;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: PLAYERS ------------------------------
;//////////////////////////////////////////////////////////////////////////////

;-----------------------------------------------------------------
;//////////////////////// LOAD PLAYERS ///////////////////////////
;-----------------------------------------------------------------
Function LoadPlayers()
 For cyc=1 To no_plays
  ;generate model
  Loader("Please Wait","Loading Character "+cyc+" of "+no_plays) 
  p(cyc)=LoadAnimMesh("Characters/Models/Model"+Dig$(charModel(pChar(cyc)),10)+".3ds")
  LoadSequences(cyc)
  ;appearance
  ApplyCostume(cyc)
  ;restore scars 
  For limb=1 To 40
   pOldScar(cyc,limb)=-1
   pScar(cyc,limb)=charScar(pChar(cyc),limb)
   If pLimb(cyc,limb)>0 And pScar(cyc,limb)=>5
    HideEntity pLimb(cyc,limb)
   EndIf
  Next
  SeverLimbs(cyc)
  ;hide weapons by default
  HideEntity FindChild(p(cyc),"Phone") 
  HideEntity FindChild(p(cyc),"Barbell") 
  For v=1 To weapList
   HideEntity FindChild(p(cyc),weapFile$(v))
  Next
  EntityAlpha FindChild(p(cyc),"Bottle"),0.9
  EntityShininess FindChild(p(cyc),"Phone"),1.0
  ;location
  pX#(cyc)=charX#(pChar(cyc)) : pZ#(cyc)=charZ#(pChar(cyc))
  pOldX#(cyc)=pX#(cyc) : pOldZ#(cyc)=pZ#(cyc)
  pTX#(cyc)=pX#(cyc) : pTZ#(cyc)=pZ#(cyc)
  pA#(cyc)=charA#(pChar(cyc)) : pTA#(cyc)=pA#(cyc)
  pY#(cyc)=charY#(pChar(cyc))+20 : pGravity#(cyc)=1.0
  PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
  RotateEntity p(cyc),0,pA#(cyc),0
  scaler#=charHeight(pChar(cyc))*0.0025
  ScaleEntity p(cyc),0.34+scaler#,0.34+scaler#,0.34+scaler#
  Animate p(cyc),1,0.5,pSeq(cyc,1),0
  ;collision detection
  pPivot(cyc)=CreatePivot()
  EntityType pPivot(cyc),1,0
  EntityRadius pPivot(cyc),8,18
  PositionEntity pPivot(cyc),pX#(cyc),pY#(cyc)+18,pZ#(cyc)
  pMovePivot(cyc)=CreatePivot()
  EntityType pMovePivot(cyc),4,0
  EntityRadius pMovePivot(cyc),8,18
  PositionEntity pMovePivot(cyc),pX#(cyc),pY#(cyc)+18,pZ#(cyc)
  ;reset values
  pAnim(cyc)=0 : pAnimTim(cyc)=0 : pState(cyc)=0
  pAgenda(cyc)=Rnd(0,2) : pNowhere(cyc)=99 
  pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
  pEyes(cyc)=2 : pOldEyes(cyc)=-1
  pHealth(cyc)=charHealth(pChar(cyc))
  pHP(cyc)=charHP(pChar(cyc))
  pInjured(cyc)=charInjured(pChar(cyc))
  pWeapon(cyc)=0 : pPhone(cyc)=0
  pSeat(cyc)=0 : pBed(cyc)=0
  pGrappling(cyc)=0 : pGrappler(cyc)=0
  For v=1 To no_plays
   pInteract(cyc,v)=0
  Next
  For v=1 To no_chairs
   pSeatFriction(cyc,v)=0
  Next
  For v=1 To no_beds
   pBedFriction(cyc,v)=0
  Next
  ;shadows
  For limb=1 To 40
   pShadow(cyc,limb)=0
   If limb=30 Or (optShadows=2 And (limb=1 Or (limb=>4 And limb=<6) Or (limb=>17 And limb=<19) Or limb=32 Or limb=33 Or limb=35 Or limb=36))
    pShadow(cyc,limb)=LoadSprite("World/Sprites/Shadow.png",2)
    ScaleSprite pShadow(cyc,limb),13,13
    If limb<>30 Then ScaleSprite pShadow(cyc,limb),10,10
    If limb=6 Or limb=19 Or limb=33 Or limb=36 Then ScaleSprite pShadow(cyc,limb),8,8
    RotateEntity pShadow(cyc,limb),90,0,0
    SpriteViewMode pShadow(cyc,limb),2
    EntityColor pShadow(cyc,limb),10,10,10 
    PositionEntity pShadow(cyc,limb),pX#(cyc),pY#(cyc),pZ#(cyc) 
   EndIf
  Next 
  ;assess control 
  charPlayer(pChar(cyc))=cyc
  pControl(cyc)=0
  If pChar(cyc)=gamChar(slot)
   pControl(cyc)=3 : camFoc=cyc
   gamPlayer(slot)=cyc
  EndIf
 Next
End Function

;-----------------------------------------------------------------
;///////////////////////// PLAYER CYCLE //////////////////////////
;-----------------------------------------------------------------
Function PlayerCycle()
 For cyc=1 To no_plays
  ;counters
  pNowhere(cyc)=pNowhere(cyc)-1
  If pNowhere(cyc)<0 Then pNowhere(cyc)=0
  pSatisfied(cyc)=pSatisfied(cyc)-1
  If pSatisfied(cyc)<0 Then pSatisfied(cyc)=0
  pRunTim(cyc)=pRunTim(cyc)-1
  If pRunTim(cyc)<0 Then pRunTim(cyc)=0
  ;service timers
  If gamPromo=0 Then charFollowTim(pChar(cyc))=charFollowTim(pChar(cyc))-gamSpeed(slot)
  If charFollowTim(pChar(cyc))<0 Or charRelation(pChar(cyc),gamChar(slot))<0 Then charFollowTim(pChar(cyc))=0
  If gamPromo=0 Then charBribeTim(pChar(cyc))=charBribeTim(pChar(cyc))-gamSpeed(slot)
  If charBribeTim(pChar(cyc))<0 Or charRelation(pChar(cyc),gamChar(slot))<0 Then charBribeTim(pChar(cyc))=0
  ;honour collision detection
  If pSeat(cyc)=0 And pBed(cyc)=0
   pGround#(cyc)=EntityY(pShadow(cyc,30))
   pX#(cyc)=EntityX(pPivot(cyc))
   pY#(cyc)=EntityY(pPivot(cyc))-18
   pZ#(cyc)=EntityZ(pPivot(cyc))
  EndIf
  ;prepare for move correction
  If pCollisions(cyc)=0
   pOldMoveX#(cyc)=EntityX(pLimb(cyc,30),1)
   pOldMoveZ#(cyc)=EntityZ(pLimb(cyc,30),1)
  EndIf
  ;enforce blocks
  If pSeat(cyc)=0 And pBed(cyc)=0
   EnforceBlocks(cyc)
  EndIf 
  ;clock stat changes
  If gotim>0 And pChar(cyc)=gamChar(slot)
   If charStrength(pChar(cyc))>charOldStrength(pChar(cyc)) Then statTim(1)=20
   If charStrength(pChar(cyc))<charOldStrength(pChar(cyc)) Then statTim(1)=-20
   If charAgility(pChar(cyc))>charOldAgility(pChar(cyc)) Then statTim(2)=20
   If charAgility(pChar(cyc))<charOldAgility(pChar(cyc)) Then statTim(2)=-20
   If charIntelligence(pChar(cyc))>charOldIntelligence(pChar(cyc)) Then statTim(3)=20
   If charIntelligence(pChar(cyc))<charOldIntelligence(pChar(cyc)) Then statTim(3)=-20
   If charReputation(pChar(cyc))>charOldReputation(pChar(cyc)) Then statTim(4)=20
   If charReputation(pChar(cyc))<charOldReputation(pChar(cyc)) Then statTim(4)=-20
  EndIf
  ;clock old stats
  charOldStrength(pChar(cyc))=charStrength(pChar(cyc))
  charOldAgility(pChar(cyc))=charAgility(pChar(cyc))
  charOldIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))
  charOldReputation(pChar(cyc))=charReputation(pChar(cyc))
  ;assess relationships
  AssessRelationships(cyc)
  ;get input
  If gotim>0
   GetInput(cyc)
   TranslateInput(cyc)
  EndIf
  ;trigger falls
  If (pHP(cyc)=<0 Or pHealth(cyc)=<0) And CollapseViable(cyc)
   randy=Rnd(0,4)
   If randy=<2 Then ChangeAnim(cyc,80)
   If randy=>3 Then ChangeAnim(cyc,83)
   If SatisfiedAngle(pA#(cyc),pHurtA#(cyc),45) And pDazed(cyc)=0 Then ChangeAnim(cyc,86)
   If pDT(cyc)<50 Then pDT(cyc)=150-pHealth(cyc)
  EndIf
  If gotim>0 And pY#(cyc)>pGround#(cyc)+5 And pAnim(cyc)<>87 And pGrappling(cyc)=0 And pGrappler(cyc)=0 And pSeat(cyc)=0 And pBed(cyc)=0
   ChangeAnim(cyc,87)
  EndIf
  ;trigger breakdown
  If gotim>0 And charHappiness(pChar(cyc))=<0 And charBreakdown(pChar(cyc))=0 And pGrappling(cyc)=0 And pGrappler(cyc)=0
   If pHealth(cyc)>0 And AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And pAnim(cyc)<>87 Then ChangeAnim(cyc,96)
  EndIf
  ;manage animations
  Animations(cyc)
  ;gravity
  If pY#(cyc)=>pOldY#(cyc)-0.5 And pY#(cyc)=<pOldY#(cyc)+0.5
   If pGravity#(cyc)<-1.0 Then pGravity#(cyc)=-1.0
  EndIf
  If pGravity#(cyc)>-10.0 Then pGravity#(cyc)=pGravity#(cyc)-0.3
  If KeyDown(201) And pControl(cyc)>0 Then pGravity#(cyc)=Rnd(2.0,10.0)
  If pSeat(cyc)>0 Or pBed(cyc)>0 Then pGravity#(cyc)=0
  MoveEntity pPivot(cyc),0,pGravity#(cyc),0 
  ;monitor status
  MonitorStatus(cyc)
  ;bind to chair/bed
  If pAnim(cyc)=>100 And pAnim(cyc)=<109
   If pAnim(cyc)=101
    pSeatX#(cyc)=pLeaveX#(cyc) : pSeatZ#(cyc)=pLeaveZ#(cyc)
    pSeatY#(cyc)=pLeaveY#(cyc) : pSeatA#(cyc)=pLeaveA#(cyc)
   Else
    If pSeat(cyc)>0
     target=FindChild(world,"Chair"+Dig$(pSeat(cyc),10))
     pSeatX#(cyc)=EntityX(target,1) : pSeatZ#(cyc)=EntityZ(target,1)
     pSeatY#(cyc)=EntityY(target,1)-13 : pSeatA#(cyc)=CleanAngle#(EntityYaw(target,1))
     If gamLocation(slot)=11 Then pSeatA#(cyc)=CleanAngle#(EntityYaw(target,1)+180)
    EndIf
    If pBed(cyc)>0
     target=FindChild(world,"Mat"+Dig$(pBed(cyc),10))
     pSeatX#(cyc)=EntityX(target,1) : pSeatZ#(cyc)=EntityZ(target,1)
     pSeatY#(cyc)=EntityY(target,1)-10 : pSeatA#(cyc)=CleanAngle#(EntityYaw(target,1)+95)
    EndIf 
   EndIf
   GetSmoothSpeeds(pX#(cyc),pSeatX#(cyc),pY#(cyc),pSeatY#(cyc),pZ#(cyc),pSeatZ#(cyc),10)
   If pX#(cyc)<pSeatX#(cyc) Then pX#(cyc)=pX#(cyc)+speedX#
   If pX#(cyc)>pSeatX#(cyc) Then pX#(cyc)=pX#(cyc)-speedX#
   If pY#(cyc)<pSeatY#(cyc) Then pY#(cyc)=pY#(cyc)+speedY#
   If pY#(cyc)>pSeatY#(cyc) Then pY#(cyc)=pY#(cyc)-speedY#
   If pZ#(cyc)<pSeatZ#(cyc) Then pZ#(cyc)=pZ#(cyc)+speedZ#
   If pZ#(cyc)>pSeatZ#(cyc) Then pZ#(cyc)=pZ#(cyc)-speedZ# 
   PositionEntity pPivot(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
   If SatisfiedAngle(pA#(cyc),pSeatA#(cyc),10)=0 Then pA#(cyc)=pA#(cyc)+ReachAngle#(pA#(cyc),pSeatA#(cyc),5)
   If SatisfiedAngle(pA#(cyc),pSeatA#(cyc),45)=0 Then pA#(cyc)=pA#(cyc)+ReachAngle#(pA#(cyc),pSeatA#(cyc),10)
   If SatisfiedAngle(pA#(cyc),pSeatA#(cyc),10) Then pA#(cyc)=pSeatA#(cyc)
   pA#(cyc)=CleanAngle#(pA#(cyc))
   PositionEntity pPivot(cyc),0,pA#(cyc),0
  EndIf
 Next
End Function

;-----------------------------------------------------------------
;////////////////////// RELATED FUNCTIONS ////////////////////////
;-----------------------------------------------------------------

;DISPLAY PLAYERS
Function DisplayPlayers()
 For cyc=1 To no_plays  
  ;manage scars
  ManageScars(cyc)
  ;facial expressions
  If GrimaceViable(cyc)
   pSpeaking(cyc)=1 : pEyes(cyc)=1
   If pAnim(cyc)=91 Or pAnim(cyc)=93 Or pAnim(cyc)=95 Then pEyes(cyc)=3
  EndIf
  FacialExpressions(cyc) 
  ;update display
  pA#(cyc)=CleanAngle(pA#(cyc))
  RotateEntity p(cyc),0,pA#(cyc),0
  RotateEntity pPivot(cyc),0,pA#(cyc),0
  PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
  If pGrappling(cyc)>0 Or pGrappler(cyc)>0
   ResetEntity pMovePivot(cyc)
   PositionEntity pMovePivot(cyc),pX#(cyc),EntityY(pPivot(cyc)),pZ#(cyc)
   EntityType pMovePivot(cyc),4,0
   EntityRadius pMovePivot(cyc),8,18
   PositionEntity pMovePivot(cyc),EntityX(pLimb(cyc,30),1),EntityY(pPivot(cyc)),EntityZ(pLimb(cyc,30),1)
  EndIf
  ;prevent falling underground
  If pY#(cyc)<0
   ResetEntity pPivot(cyc)
   PositionEntity pPivot(cyc),0,50,0
   If gamLocation(slot)=2 Then PositionEntity pPivot(cyc),80,50,230
   EntityType pPivot(cyc),1,0
   EntityRadius pPivot(cyc),8,18
  EndIf
  ;core shadow
  ResetEntity pShadow(cyc,30)
  PositionEntity pShadow(cyc,30),EntityX(pLimb(cyc,30),1),EntityY(pLimb(cyc,30),1),EntityZ(pLimb(cyc,30),1)
  RotateEntity pShadow(cyc,30),90,EntityYaw(pLimb(cyc,30),1),0
  EntityType pShadow(cyc,30),2,0
  EntityRadius pShadow(cyc,30),2,0.75
  MoveEntity pShadow(cyc,30),0,0,250
  ;If pSeat(cyc)>0 Then EntityType pShadow(cyc,30),0,0 : PositionEntity pShadow(cyc,30),pSeatX#(cyc),pSeatY#(cyc)+2.0,pSeatZ#(cyc)
  ;If pBed(cyc)>0 Then EntityType pShadow(cyc,30),0,0 : PositionEntity pShadow(cyc,30),pSeatX#(cyc),EntityY(pLimb(cyc,30),1)-3.5,pSeatZ#(cyc)
  If pAnim(cyc)=100 Or pAnim(cyc)=101 Or pSeat(cyc)>0 Or pBed(cyc)>0 Then EntityType pShadow(cyc,30),0,0 : PositionEntity pShadow(cyc,30),EntityX(pLimb(cyc,30),1),pLeaveY#(cyc)-4.5,EntityZ(pLimb(cyc,30),1)
  If optShadows=0
   EntityAlpha pShadow(cyc,30),0
  Else 
   If optShadows=1 Then EntityAlpha pShadow(cyc,30),0.5
   If optShadows=2 Then EntityAlpha pShadow(cyc,30),0.2
  EndIf
  ;additional shadows
  If optShadows=2
   For limb=1 To 40
    If limb<>30 And pShadow(cyc,limb)>0
     RotateEntity pShadow(cyc,limb),90,EntityYaw(pLimb(cyc,limb),1),0
     PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),pGround#(cyc),EntityZ(pLimb(cyc,limb),1)
     ;If pSeat(cyc)>0 Then PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),pSeatY#(cyc)+2.0,EntityZ(pLimb(cyc,limb),1)
     ;If pBed(cyc)>0 Then PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),EntityY(pLimb(cyc,30),1)-3.5,EntityZ(pLimb(cyc,limb),1)
     If pAnim(cyc)=100 Or pAnim(cyc)=101 Or pSeat(cyc)>0 Or pBed(cyc)>0 Then PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),pLeaveY#(cyc)-4.5,EntityZ(pLimb(cyc,limb),1)
     EntityAlpha pShadow(cyc,limb),0.1
     If limb=6 Or limb=19 Then EntityAlpha pShadow(cyc,limb),0.15
    EndIf
   Next
  EndIf
 Next
End Function

;FIND CHARACTER (as player)
Function FindChar(char)
 value=0
 For v=1 To no_plays
  If pChar(v)=char Then value=v
 Next
 Return value
End Function

;SCAR SPECIFIC LIMB
Function ScarLimb(cyc,limb,chance)
 If limb=0 Then chance=chance*2
 randy=Rnd(0,chance)
 If randy=0 And pScar(cyc,limb)=<4
  ;add scarring
  pScar(cyc,limb)=pScar(cyc,limb)+1 
  If pScar(cyc,limb)>4 Then pScar(cyc,limb)=4
  If pScar(cyc,limb)=>2 And limb>0 And pLimb(cyc,limb)>0
   vol#=pScar(cyc,limb)*0.1
   ProduceSound(p(cyc),sBleed,22050,vol#)
   limbX#=EntityX(pLimb(cyc,limb),1)
   limbY#=EntityY(pLimb(cyc,limb),1)
   limbZ#=EntityZ(pLimb(cyc,limb),1)
   CreatePool(limbX#,pGround#(cyc),limbZ#,Rnd(1.0,5.0),1,1)
   If pScar(cyc,limb)=>3 Then LoseLimb(cyc,limb,chance)
  EndIf
  ;lose accessories
  If limb=1
   randy=Rnd(0,8)
   If randy=0 And charSpecs(pChar(cyc))>0 Then HideEntity FindChild(p(cyc),"Specs")
   If randy=>1 And randy=<2 And charSpecs(pChar(cyc))>0 Then HideEntity FindChild(p(cyc),"Lens0"+randy)
   pDazed(cyc)=Rnd(50,200)
  EndIf 
 EndIf
 ;risk blinding
 randy=Rnd(0,100)
 If randy=0 And limb=1 Then pDazed(cyc)=Rnd(50,200)
End Function

;SCAR WHOLE BODY
Function ScarArea(cyc,x#,y#,z#,chance)
 For limb=0 To 40
  If limb=1 Then risk=chance*2 Else risk=chance
  If x#=0 And y#=0 And z#=0 
   ScarLimb(cyc,limb,risk)
  Else
   If pLimb(cyc,limb)>0
    limbX#=EntityX(pLimb(cyc,limb),1)
    limbY#=EntityY(pLimb(cyc,limb),1)
    limbZ#=EntityZ(pLimb(cyc,limb),1)
    If x#>limbX#-10 And x#<limbX#+10 And z#>limbZ#-10 And z#<limbZ#+10 And y#>limbY#-10 And y#<limbY#+10
     ScarLimb(cyc,limb,chance)
    EndIf
   EndIf
  EndIf
 Next
End Function

;SEVER LIMBS
Function SeverLimbs(cyc) 
 ;ears
 If pScar(cyc,37)=5 And pScar(cyc,38)=5 Then EntityTexture pLimb(cyc,1),tSeverEars,0,6
 ;torso
 If pScar(cyc,4)=5 And pScar(cyc,17)=<4 Then EntityTexture pLimb(cyc,3),tSeverBody(1),0,6
 If pScar(cyc,4)=<4 And pScar(cyc,17)=5 Then EntityTexture pLimb(cyc,3),tSeverBody(2),0,6
 If pScar(cyc,4)=5 And pScar(cyc,17)=5 Then EntityTexture pLimb(cyc,3),tSeverBody(3),0,6
 ;arms
 For limb=4 To 29
  If pScar(cyc,limb)=5
   If limbSource(limb)=>6 And limbSource(limb)=<16 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverArm(1),0,6
   If limbSource(limb)=>19 And limbSource(limb)=<29 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverArm(1),0,6
   If limbSource(limb)=5 Or limbSource(limb)=18 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverArm(2),0,6
   If limbSource(limb)=4 Or limbSource(limb)=17 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverArm(3),0,6  
  EndIf
 Next
 ;legs
 For limb=30 To 36
  If pScar(cyc,limb)=5
   If limbSource(limb)=32 Or limbSource(limb)=35 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverLegs(1),0,6
   If limbSource(limb)=31 Or limbSource(limb)=34 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverLegs(2),0,6
   If limbSource(limb)=30 Then EntityTexture pLimb(cyc,limbSource(limb)),tSeverLegs(3),0,6
  EndIf
 Next
End Function

;LOSE LIMB
Function LoseLimb(cyc,limb,chance)
 ;assess significance
 major=MajorLimb(limb)
 ;risk loss
 If chance<5 Then chance=5
 If major=1 Then chance=chance*2
 If (limbPrecede(limb)>0 And pScar(cyc,limbPrecede(limb))=<1) Or (limbSource(limb)>0 And pScar(cyc,limbSource(limb))=<1)
  chance=chance*2
 EndIf
 randy=Rnd(0,chance)
 If randy=0 And optGore=>3 And limbSource(limb)>0 And pScar(cyc,limbSource(limb))=<4
  If (pScar(cyc,limbPrecede(limb))=>2 Or limbPrecede(limb)=0) And (pScar(cyc,limbSource(limb))=>2 Or limbSource(limb)=0)
   ;pain and mess
   ProduceSound(p(cyc),sBleed,22050,1)
   limbX#=EntityX(pLimb(cyc,limb),1)
   limbY#=EntityY(pLimb(cyc,limb),1)
   limbZ#=EntityZ(pLimb(cyc,limb),1)
   If major=0
    If pHealth(cyc)>0 Then ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,1)
    CreateSpurt(limbX#,limbY#,limbZ#,1,5,3)
    CreatePool(limbX#,pGround#(cyc),limbZ#,Rnd(3.0,10.0),2,1) 
    RiskInjury(cyc,50)
   EndIf
   If major=1
    If pHealth(cyc)>0 Then ProduceSound(p(cyc),sAgony(Rnd(1,3)),22050,1)
    CreateSpurt(limbX#,limbY#,limbZ#,3,15,3)
    CreatePool(limbX#,pGround#(cyc),limbZ#,Rnd(5.0,15.0),5,1) 
    pHealth(cyc)=pHealth(cyc)/2
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))/2
    If pInjured(cyc)<1000 Then pInjured(cyc)=1000
   EndIf
   DropWeapon(cyc,0)
   pHP(cyc)=0
   ;remove limb
   pScar(cyc,limb)=5
   If limbPrecede(limb)>0 Then pScar(cyc,limbPrecede(limb))=5
   SeverLimbs(cyc)
   HideEntity pLimb(cyc,limb)
   ;risk punishment
   If gamMission(slot)=14 And pChar(cyc)=gamTarget(slot) Then CompleteMission(1)
   If charAttacker(pChar(cyc))=gamChar(slot)
    If charPromo(pChar(cyc),gamChar(slot))=0 Then charPromo(pChar(cyc),gamChar(slot))=54
    For v=1 To no_plays
     If charRole(pChar(v))=1 And (Friendly(v,gamPlayer(slot))=0 Or Friendly(v,cyc)) And charBribeTim(pChar(v))=0 And gamBlackout(slot)=0 And InProximity(v,gamPlayer(slot),50) And AttackViable(v)=>1 And AttackViable(v)=<2 And pDazed(v)=0
      If InLine(v,p(gamPlayer(slot)),60) Or InLine(v,p(cyc),60)
       randy=Rnd(0,2)
       If (randy=0 Or major=1) And gamWarrant(slot)<11 Then gamWarrant(slot)=11 : gamVictim(slot)=pChar(cyc)
      EndIf
     EndIf
    Next
   EndIf 
  EndIf
 EndIf
End Function

;MANAGE SCARS
Function ManageScars(cyc)
 For limb=0 To 40
  If pScar(cyc,limb)=5 And MajorLimb(limb) And pInjured(cyc)<1000 Then pInjured(cyc)=1000
  If pScar(cyc,limb)=<4 And pLimb(cyc,limb)>0
   ;heal scars
   randy=Rnd(0,5000/gamSpeed(slot))
   If gamLocation(slot)=11 And pX#(cyc)>50 And pZ#(cyc)>30 Then randy=Rnd(0,50/gamSpeed(slot))
   If randy=0 Or (randy=1 And limb=1) Then pScar(cyc,limb)=pScar(cyc,limb)-1
   If pScar(cyc,limb)<0 Then pScar(cyc,limb)=0
   ;prevent gore
   If (optGore=0 Or limb=0) And pScar(cyc,limb)>1 Then pScar(cyc,limb)=1
   ;apply scars (standard)
   If pScar(cyc,limb)<>pOldScar(cyc,limb)
    If limb=1
     EntityTexture pLimb(cyc,1),tFaceScar(pScar(cyc,limb)),0,5
     EntityTexture pLimb(cyc,2),tFaceScar(pScar(cyc,limb)),0,5
     EntityTexture pLimb(cyc,37),tFaceScar(pScar(cyc,limb)),0,5
     EntityTexture pLimb(cyc,38),tFaceScar(pScar(cyc,limb)),0,5
    EndIf
    If limb=3 Then EntityTexture pLimb(cyc,limb),tBodyScar(pScar(cyc,limb)),0,5
    If limb=>4 And limb=<29 Then EntityTexture pLimb(cyc,limb),tArmScar(pScar(cyc,limb)),0,5
    If limb=>30 And limb=<36 Then EntityTexture pLimb(cyc,limb),tLegScar(pScar(cyc,limb)),0,5
    If pScar(cyc,limb)<pOldScar(cyc,limb) And pOldScar(cyc,limb)>1 And gamLocation(slot)=11 And pX#(cyc)>50 And pZ#(cyc)>30
     pHealth(cyc)=pHealth(cyc)+Rnd(0,1) 
     charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(0,1)
    EndIf
   EndIf
   ;store status
   pOldScar(cyc,limb)=pScar(cyc,limb)
  EndIf
 Next 
End Function

;COUNT SCARS
Function CountScars(cyc)
 value=0
 For limb=1 To 40
  If pLimb(cyc,limb)>0 And pScar(cyc,limb)=>2 And limbSource(limb)<>6 And limbSource(limb)<>19
   If limb=3 Then value=value+2 Else value=value+1
  EndIf
 Next 
 Return value
End Function

;RISK INJURY
Function RiskInjury(cyc,chance)
 randy=Rnd(0,chance)
 If randy=0 And pInjured(cyc)=0
  If pHealth(cyc)>0 Then ProduceSound(p(cyc),sAgony(Rnd(1,3)),22050,1) 
  pHealth(cyc)=pHealth(cyc)/2
  charHappiness(pChar(cyc))=charHappiness(pChar(cyc))/2
  charStrength(pChar(cyc))=charStrength(pChar(cyc))-(charStrength(pChar(cyc))/5)
  charAgility(pChar(cyc))=charAgility(pChar(cyc))-(charAgility(pChar(cyc))/5)
  pInjured(cyc)=Rnd(1000,100000) ;3600=hour, 86400=day
  If gamMission(slot)=14 And pChar(cyc)=gamTarget(slot) Then CompleteMission(1)
 EndIf
End Function

;MONITOR HEALTH STATUS
Function MonitorStatus(cyc)
 ;monitor attributes
 If pChar(cyc)=gamChar(slot)
  randy=Rnd(0,15000)
  If randy=1 And pState(cyc)<>108 And pAnim(cyc)<>132 Then charStrength(pChar(cyc))=charStrength(pChar(cyc))-1
  If randy=2 And pAnim(cyc)<>13 Then charAgility(pChar(cyc))=charAgility(pChar(cyc))-1
  If randy=3 And pState(cyc)<>102 And pState(cyc)<>109 Then charIntelligence(pChar(cyc))=charIntelligence(pChar(cyc))-1
  If randy=4 And charReputation(pChar(cyc))>50 Then charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
  randy=Rnd(0,25000)
  If randy=0 And gamGrowth(slot)=0 Then gamGrowth(slot)=Rnd(-1,1)
  randy=Rnd(0,300)
  If randy=0 And gamLocation(slot)=2 And pAnim(cyc)=13 And pAnimTim(cyc)>20 And pNowhere(cyc)=0
   charAgility(pChar(cyc))=charAgility(pChar(cyc))+1
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,2)
   pHealth(cyc)=pHealth(cyc)-1
   randy=Rnd(0,50)
   If randy=0 And gamGrowth(slot)=>0 Then gamGrowth(slot)=gamGrowth(slot)-1
  EndIf 
 EndIf
 ;monitor happiness
 If pChar(cyc)=gamChar(slot) And pAnim(cyc)<>93 And pAnim(cyc)<>95 And pAnim(cyc)<>103
  randy=Rnd(1,400)
  If gamLocation(slot)=9 And pAnim(cyc)=102 And pSeat(cyc)=>1 And pSeat(cyc)=<6 Then entertain=1 Else entertain=0
  If randy=<gamSpeed(slot) And entertain=0 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1 
 EndIf
 If charBreakdown(pChar(cyc))>0 And charHappiness(pChar(cyc))<50 Then charHappiness(pChar(cyc))=50
 ;monitor health
 If pChar(cyc)=gamChar(slot) And pHealth(cyc)>1 And pAnim(cyc)<>94 And pAnim(cyc)<>103
  randy=Rnd(1,400+(charAgility(pChar(cyc))*2))
  If pAnim(cyc)=13 Then randy=Rnd(1,200+(charAgility(pChar(cyc))*2))
  If pAnim(cyc)=102 And pState(cyc)=101 Then randy=Rnd(1,1000)
  If pAnim(cyc)=102 And pState(cyc)=103 Then randy=99
  If randy=<gamSpeed(slot) Then pHealth(cyc)=pHealth(cyc)-1
 EndIf
 If pHealth(cyc)<0 Then pHealth(cyc)=0 
 If pHealth(cyc)>100 Then pHealth(cyc)=100 
 charHealth(pChar(cyc))=pHealth(cyc)
 ;injury pain
 pInjured(cyc)=pInjured(cyc)-gamSpeed(slot)
 If pInjured(cyc)<0 Then pInjured(cyc)=0
 charInjured(pChar(cyc))=pInjured(cyc)
 If charBreakdown(pChar(cyc))=gamSpeed(slot) Then pHP(cyc)=0
 If gamPromo=0 Then charBreakdown(pChar(cyc))=charBreakdown(pChar(cyc))-gamSpeed(slot)
 If charBreakdown(pChar(cyc))<0 Then charBreakdown(pChar(cyc))=0
 If (pInjured(cyc)>0 Or charBreakdown(pChar(cyc))>0) And (pHealth(cyc)>1 Or pChar(cyc)=gamChar(slot)) And pAnim(cyc)<>103
  randy=Rnd(0,300)
  If randy=<gamSpeed(slot)
   pHealth(cyc)=pHealth(cyc)-1 : pHP(cyc)=pHP(cyc)-1
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-Rnd(0,1)
  EndIf
 EndIf
 If (pScar(cyc,31)=5 Or pScar(cyc,32)=5) And (pScar(cyc,34)=5 Or pScar(cyc,35)=5) Then pHP(cyc)=0
 ;dazed state
 pDazed(cyc)=pDazed(cyc)-1
 If pDazed(cyc)<0 Then pDazed(cyc)=0
 If pDazed(cyc)>0
  randy=Rnd(0,100)
  If randy=<1 And pAnim(cyc)=>10 And pAnim(cyc)=<19 Then pHP(cyc)=pHP(cyc)-1 : ProduceSound(p(cyc),sPain(Rnd(1,4)),22050,0)
  DropWeapon(cyc,10)
 EndIf
 ;down time
 pDT(cyc)=pDT(cyc)-1
 If pDT(cyc)<0 Then pDT(cyc)=0  
 randy=Rnd(0,500)
 If randy=<2 Then pDT(cyc)=pDT(cyc)-50
 If randy=>3 And randy=<4 Then pDT(cyc)=pDT(cyc)-100
 If randy=5 Then pDT(cyc)=0
 If cyc=promoActor(1) Or cyc=promoActor(2) Then pDT(cyc)=0
 ;monitor speed
 pSpeed#(cyc)=0.5+(Float(charAgility(pChar(cyc)))/100)
 ;health=100-charHealth(pChar(cyc))
 ;factor#=pSpeed#(cyc)/500
 ;drag#=health*factor#
 ;pSpeed#(cyc)=pSpeed#(cyc)-drag#
 If pHealth(cyc)<10 And pInjured(cyc)=0 Then pSpeed#(cyc)=pSpeed#(cyc)-(pSpeed#(cyc)/5)
 If pInjured(cyc)>0 Then pSpeed#(cyc)=pSpeed#(cyc)-(pSpeed#(cyc)/3)
 If pScar(cyc,31)=5 Or pScar(cyc,32)=5 Or pScar(cyc,34)=5 Or pScar(cyc,35)=5 Then pSpeed#(cyc)=pSpeed#(cyc)/2
 If pDazed(cyc)>0 Then pSpeed#(cyc)=0.5
 ;footsteps
 If pStepTim(cyc)>20/pSpeed#(cyc)
  If cyc=camFoc Then ProduceSound(p(cyc),sStep(Rnd(3,4)),22050,Rnd(0.25,0.5))
  If cyc<>camFoc Then ProduceSound(p(cyc),sStep(Rnd(3,4)),22050,Rnd(0.0,0.2)) 
  pStepTim(cyc)=0
 EndIf
 ;machine gun fire
 If pAnim(cyc)=60 And pAnimTim(cyc)>5
  EntityFX FindChild(p(cyc),"FlameA"),1
  EntityAlpha FindChild(p(cyc),"FlameA"),Rnd(-0.2,1.0) 
 Else 
  EntityAlpha FindChild(p(cyc),"FlameA"),0
 EndIf
 ;pistol fire
 If pAnim(cyc)=61 And (pFireTim(cyc)=>4 Or pFireTim(cyc)=<-4)
  EntityFX FindChild(p(cyc),"FlameB"),1
  EntityAlpha FindChild(p(cyc),"FlameB"),Rnd(0.1,0.8) 
 Else 
  EntityAlpha FindChild(p(cyc),"FlameB"),0
 EndIf
 ;weightlifting
 If gamLocation(slot)=2 And pAnim(cyc)=102 And pState(cyc)=108 And pAnimTim(cyc)=>10
  ShowEntity FindChild(p(cyc),"Barbell")
  If gamLocation(slot)=2 Then HideEntity FindChild(world,"Barbell0"+pSeat(cyc))
 Else
  HideEntity FindChild(p(cyc),"Barbell")
  For count=1 To 3
   If gamLocation(slot)=2 And (ChairTaken(count)=0 Or pSeat(cyc)=count) Then ShowEntity FindChild(world,"Barbell0"+count)
  Next
 EndIf
 ;clock weapon use
 For v=1 To no_weaps
  pWeaponTim(cyc,v)=pWeaponTim(cyc,v)-1
  If pWeaponTim(cyc,v)<0 Then pWeaponTim(cyc,v)=0
 Next
 If pWeapon(cyc)>0 Then pWeaponTim(cyc,pWeapon(cyc))=20
End Function

;IMPROVE STRENGTH
Function GainStrength(cyc,chance)
 randy=Rnd(0,chance)
 If randy=<1
  charStrength(pChar(cyc))=charStrength(pChar(cyc))+1
  charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+Rnd(1,5)
 EndIf
End Function

;DAMAGE REPUTATION
Function DamageRep(cyc,v,level)
 charHappiness(pChar(v))=charHappiness(pChar(v))-(1+level)
 If pHealth(v)>0 And charWitness(pChar(cyc))>0
  randy=Rnd(0,1)
  If randy=0 Then charReputation(pChar(v))=charReputation(pChar(v))-Rnd(level,1) 
  charReputation(pChar(cyc))=charReputation(pChar(cyc))+Rnd(level,(1+charRole(pChar(v))))
 EndIf
End Function

;LIMIT STATS
Function LimitStats(char)
 ;physical 
 If charHealth(char)<0 Then charHealth(char)=0
 If charHealth(char)>100 Then charHealth(char)=100
 If charStrength(char)<30 Then charStrength(char)=30
 If charStrength(char)>99 Then charStrength(char)=99
 If charAgility(char)<30 Then charAgility(char)=30
 If charAgility(char)>99 Then charAgility(char)=99
 ;mental
 If charHappiness(char)<0 Then charHappiness(char)=0
 If charHappiness(char)>100 Then charHappiness(char)=100 
 If charIntelligence(char)<30 Then charIntelligence(char)=30
 If charIntelligence(char)>99 Then charIntelligence(char)=99
 If charReputation(char)<30 Then charReputation(char)=30
 If charReputation(char)>99 Then charReputation(char)=99
End Function