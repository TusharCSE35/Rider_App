package org.example.project.model

enum class IncidentType(val label: String) {
    ACCIDENT("Accident"),
    VEHICLE("Vehicle breakdown"),
    UNSAFE("Unsafe area / harassment"),
    OTHER("Other")
}