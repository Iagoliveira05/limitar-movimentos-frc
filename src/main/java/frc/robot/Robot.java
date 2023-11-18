// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private XboxController xboxController;
  private CANSparkMax motor;


  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    xboxController = new XboxController(0);

    motor = new CANSparkMax(0, MotorType.kBrushless);
    motor.restoreFactoryDefaults();

    motor.enableSoftLimit(SoftLimitDirection.kForward, true); // Habilita o limite suave para movimentos para a frente
    motor.enableSoftLimit(SoftLimitDirection.kReverse, true); // Habilita o limite suave para movimentos para a atrás

    motor.setSoftLimit(SoftLimitDirection.kForward, 15);  // Posição limite para frente
    motor.setSoftLimit(SoftLimitDirection.kReverse, 0); // Posição limite para atrás


    SmartDashboard.putBoolean("Forward Soft Limit Enabled", motor.isSoftLimitEnabled(CANSparkMax.SoftLimitDirection.kForward));
    SmartDashboard.putBoolean("Reverse Soft Limit Enabled", motor.isSoftLimitEnabled(CANSparkMax.SoftLimitDirection.kReverse));                          
    SmartDashboard.putNumber("Forward Soft Limit", motor.getSoftLimit(CANSparkMax.SoftLimitDirection.kForward));
    SmartDashboard.putNumber("Reverse Soft Limit", motor.getSoftLimit(CANSparkMax.SoftLimitDirection.kReverse));

  }


  @Override
  public void robotPeriodic() {}


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    motor.set(xboxController.getLeftY());

    //#region Verifica as entradas no Shuffleboard
    
    motor.enableSoftLimit(SoftLimitDirection.kForward, SmartDashboard.getBoolean("Forward Soft Limit Enabled", true)); 
    motor.enableSoftLimit(SoftLimitDirection.kReverse, SmartDashboard.getBoolean("Reverse Soft Limit Enabled", true));

    motor.setSoftLimit(SoftLimitDirection.kForward, (float)SmartDashboard.getNumber("Forward Soft Limit", 15)); // 15 caso não tenha achado nenhum valor
    motor.setSoftLimit(SoftLimitDirection.kReverse, (float)SmartDashboard.getNumber("Reverse Soft Limit", 0));  // 0 caso não tenha acho nenhum valor
    //#endregion
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
