package org.example.project.model

enum class DocStatus(val label: String) {
    VERIFIED("Verified"),
    PENDING("Under review"),
    REJECTED("Rejected"),
    MISSING("Not uploaded")
}

data class RiderDocument(

    val name: String,
    val status: DocStatus
)